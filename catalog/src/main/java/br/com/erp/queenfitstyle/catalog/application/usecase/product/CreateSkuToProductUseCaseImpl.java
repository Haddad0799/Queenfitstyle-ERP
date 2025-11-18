package br.com.erp.queenfitstyle.catalog.application.usecase.product;

import br.com.erp.queenfitstyle.catalog.application.command.CreateSkuToProductCommand;
import br.com.erp.queenfitstyle.catalog.application.exception.color.ColorNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.product.SkuDuplicateException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.application.port.in.CreateSkuToProductUseCase;
import br.com.erp.queenfitstyle.catalog.application.port.out.ColorRepositoryPort;
import br.com.erp.queenfitstyle.catalog.application.port.out.ProductRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Inventory;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Size;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateSkuToProductUseCaseImpl implements CreateSkuToProductUseCase {

    private final ProductRepositoryPort productRepository;
    private final ColorRepositoryPort colorRepository;

    public CreateSkuToProductUseCaseImpl(ProductRepositoryPort productRepository, ColorRepositoryPort colorRepository) {
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
    }

    @Override
    @Transactional
    public Sku execute(CreateSkuToProductCommand command) {

        Product product = productRepository
                .findById(command.productId())
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto não encontrado com o ID fornecido: " + command.productId()));

        Color color = colorRepository.findByid(command.colorId())
                .orElseThrow(() -> new ColorNotFoundException(
                        "Cor não encontrada com o ID fornecido: " + command.colorId()));

        Size skuSize = new Size(command.size());
        SkuCode skuCode = new SkuCode(product.getCode(), color, skuSize);

        if (productRepository.existsAnySkuBySkuCode(skuCode.value())) {
            throw new SkuDuplicateException("Já existe um SKU com essa cor e tamanho para o produto: " + product.getCode());
        }

        Price price = new Price(command.price());
        Inventory inventory = new Inventory(command.inventory());

        Sku newSku = new Sku(skuCode, color, skuSize, price, inventory);

        product.addSku(newSku);

        Product saved = productRepository.save(product);

        return saved.getSkus().stream()
                .filter(s -> s.getCode().equals(newSku.getCode()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Erro ao salvar SKU: não encontrado após persistência (" + newSku.getCode() + ")"
                ));

    }

}
