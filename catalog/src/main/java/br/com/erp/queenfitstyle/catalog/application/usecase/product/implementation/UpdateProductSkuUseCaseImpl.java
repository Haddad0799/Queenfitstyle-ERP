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
        // 1️⃣ Busca o produto pelo SKU (aggregate root)
        Product product = productRepository
                .findBySkuCode(command.skuCode())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Não existe essa variação de produto " + command.skuCode()));

        // 2️⃣ Busca o SKU específico dentro do produto
        Sku sku = product.getSkus().stream()
                .filter(s -> s.getCode().equals(command.skuCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "SKU não encontrado para o produto " + product.getCode()));

        // 3️⃣ Atualiza os valores da SKU
        if(command.price() != null)  sku.changePrice(new Price(command.price()));

        if(command.inventory() != 0) sku.changeInventory(new Inventory(command.inventory()));

        // 4️⃣ Salva o produto (aggregate root)
        return productRepository.save(product);
    }

}
