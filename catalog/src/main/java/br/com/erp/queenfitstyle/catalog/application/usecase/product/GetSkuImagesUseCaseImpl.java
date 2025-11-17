package br.com.erp.queenfitstyle.catalog.application.usecase.product;

import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.sku.SkuNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Image;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.application.port.in.GetSkuImagesUseCase;
import br.com.erp.queenfitstyle.catalog.application.port.out.ProductRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSkuImagesUseCaseImpl implements GetSkuImagesUseCase {

    private final ProductRepositoryPort productRepository;

    public GetSkuImagesUseCaseImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<Image> execute(Long productId, String skuCode) {

        Product product = productRepository
                .findProductWithSku(productId, skuCode)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto com ID " + productId + " e SKU " + skuCode + " não encontrado"));

        Sku sku = product.getSkus().stream()
                .filter(s -> s.getCode().equals(skuCode))
                .findFirst()
                .orElseThrow(() -> new SkuNotFoundException(
                        "SKU com código " + skuCode + " não encontrado para o produto " + productId,
                        HttpStatus.NOT_FOUND));

        return sku.getImages();
    }
}
