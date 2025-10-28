package br.com.erp.queenfitstyle.catalog.application.usecase.product.implementation;

import br.com.erp.queenfitstyle.catalog.application.event.SkuErrorEventPublisher;
import br.com.erp.queenfitstyle.catalog.application.exception.ColorNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductDuplicateException;
import br.com.erp.queenfitstyle.catalog.application.service.ProductCodeGenerator;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.CreateProductCommand;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.CreateSkuCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.port.in.CreateProductUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ColorRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {

   private final ProductRepositoryPort productRepository;
   private final CategoryRepositoryPort categoryRepository;
   private final ColorRepositoryPort colorRepository;
   private final ProductCodeGenerator productCodeGenerator;
    private final SkuErrorEventPublisher skuErrorEventPublisher;

    public CreateProductUseCaseImpl(ProductRepositoryPort productRepository, CategoryRepositoryPort categoryRepository, ColorRepositoryPort colorRepository, ProductCodeGenerator productCodeGenerator, SkuErrorEventPublisher skuErrorEventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.colorRepository = colorRepository;
        this.productCodeGenerator = productCodeGenerator;
        this.skuErrorEventPublisher = skuErrorEventPublisher;
    }
    
    @Override
    @Transactional
    public Product execute(CreateProductCommand command) {

        Category category = validateExistingCategory(command.categoryId());

        validateDuplicateProductEntry(command.name(), category);

        Price productBasePrice = new Price(command.basePrice());
        ProductCode productCode = productCodeGenerator.nextCode();
        Product product = new Product(command.name(), productCode,command.description(),category,productBasePrice);

        Set<Sku> skus = buildSkus(command.skus(), productCode);

        product.addSkus(skus);

        return productRepository.save(product);
    }

    private Category validateExistingCategory(Long categoryId) {
       return categoryRepository
                .findByIdAndActiveTrue(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException(
                        "Nenhuma categoria ativa encontrada com o ID fornecido: " + categoryId));
    }

    private void validateDuplicateProductEntry(String name, Category category) {

        if(productRepository.existsByNameAndCategoryId(name,category.getId())) {
            throw new ProductDuplicateException("O produto "
                    + name +
                    " da categoria "
                    + category.getDisplayName() +
                    " já existe no sistema");
        }
    }

    private Set<Sku> buildSkus(List<CreateSkuCommand> commands, ProductCode productCode) {
        Set<Sku> skus = new HashSet<>();
        List<String> errors = new ArrayList<>();

        for (CreateSkuCommand skuCommand : commands) {
            try {
                Color color = validateExistingColor(skuCommand.colorId());
                Size size = new Size(skuCommand.size());
                Price price = new Price(skuCommand.price());
                Inventory inventory = new Inventory(skuCommand.inventory());
                SkuCode skuCode = new SkuCode(productCode, color, size);
                skus.add(new Sku(skuCode, color, size, price, inventory));
            } catch (Exception e) {
                errors.add("Falha ao criar SKU [Cor: %d, Tamanho: %s]: %s"
                        .formatted(skuCommand.colorId(), skuCommand.size(), e.getMessage()));
            }
        }
        // Publica os erros de forma desacoplada sem bloquear persistência de SKUS que não possuem falhas.
        skuErrorEventPublisher.publish(errors);

        return skus;
    }

    private Color validateExistingColor(Long colorId) {
        return colorRepository
                .findByid(colorId)
                .orElseThrow(()-> new ColorNotFoundException(
                        "Nenhuma cor encontrada para o ID fornecido: " + colorId));
    }
}
