package br.com.erp.queenfitstyle.catalog.application.usecase.product.implementation;

import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.UpdateSkuCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.port.in.UpdateProductSkuUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Inventory;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductSkuUseCaseImpl implements UpdateProductSkuUseCase {

    private final ProductRepositoryPort productRepository;

    public UpdateProductSkuUseCaseImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product execute(UpdateSkuCommand command) {

        Product product = productRepository.findProductWithSku(command.productId(), command.skuCode())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto com ID " + command.productId() + " e SKU " + command.skuCode() + " não encontrado"
                ));

        Sku sku = product.getSkus().iterator().next();

        if(command.price() != null)  sku.changePrice(new Price(command.price()));
        if(command.inventory() != 0) sku.changeInventory(new Inventory(command.inventory()));

        return productRepository.save(product);
    }


}
