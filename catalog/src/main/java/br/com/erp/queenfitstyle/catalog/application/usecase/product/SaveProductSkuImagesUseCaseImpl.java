package br.com.erp.queenfitstyle.catalog.application.usecase.product;

import br.com.erp.queenfitstyle.catalog.application.command.CreateImageCommand;
import br.com.erp.queenfitstyle.catalog.application.command.SaveProductSkuImagesCommand;
import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.sku.SkuNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Image;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.application.port.in.SaveProductSkuImagesUseCase;
import br.com.erp.queenfitstyle.catalog.application.port.out.ProductRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SaveProductSkuImagesUseCaseImpl implements SaveProductSkuImagesUseCase {

    private final ProductRepositoryPort productRepository;

    public SaveProductSkuImagesUseCaseImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public void execute(SaveProductSkuImagesCommand commmand) {

        Product product = productRepository
                .findProductWithSku(commmand.productId(), commmand.skuCode())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto com ID " + commmand.productId() + " e SKU " + commmand.skuCode() + " não encontrado"));

        Sku sku = product.getSkus().stream()
                .filter(s -> s.getCode().equals(commmand.skuCode()))
                .findFirst()
                .orElseThrow(() -> new SkuNotFoundException(
                        "SKU com código " + commmand.skuCode() + " não encontrado para o produto " + commmand.productId(),
                        HttpStatus.NOT_FOUND));


        for (CreateImageCommand command : commmand.imagesCommand()) {
            if (sku.getImages().size() >= 3) {
                throw new IllegalStateException("O SKU " + sku.getCode() + " já possui o número máximo de imagens (3)");
            }

            Image image = Image.create(command.filename(), command.publicUrl(), command.displayOrder());
            sku.addImage(image);
        }

        productRepository.save(product);
    }
}
