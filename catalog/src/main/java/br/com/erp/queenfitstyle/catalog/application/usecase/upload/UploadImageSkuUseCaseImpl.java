package br.com.erp.queenfitstyle.catalog.application.usecase.upload;

import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.sku.SkuNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.port.in.UploadImageSkuUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.port.out.UploadStoragePort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadImageSkuUseCaseImpl implements UploadImageSkuUseCase {

    private final ProductRepositoryPort productRepository;
    private final UploadStoragePort uploadStorage;

    public UploadImageSkuUseCaseImpl(ProductRepositoryPort productRepository, UploadStoragePort uploadStorage) {
        this.productRepository = productRepository;
        this.uploadStorage = uploadStorage;
    }

    @Override
    public List<String> execute(Long productId, String skuCode, List<String> filenames) {

        Product product = productRepository
                .findProductWithSku(productId, skuCode)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto com ID " + productId + " e SKU " + skuCode + " não encontrado"));


        // Extrai o SKU carregado
        Sku sku = product.getSkus().stream()
                .findFirst()
                .orElseThrow(()-> new SkuNotFoundException(
                        "Nenhum sku encontrado com o código " + skuCode, HttpStatus.NOT_FOUND));

        // Define o prefixo do caminho dentro do bucket
        String pathPrefix = generatePathPrefix(product.getSlug(), sku.getCode());

        // Gera e retorna as URLs pré-assinadas
        return uploadStorage.generatePresignedUrls(pathPrefix, filenames);
    }

    private String generatePathPrefix(String productSlug, String skuCode) {
        return "products/" + productSlug + "/skus/" + skuCode;
    }
}


