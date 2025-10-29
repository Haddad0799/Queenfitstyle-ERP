package br.com.erp.queenfitstyle.catalog.application.usecase.product;

import br.com.erp.queenfitstyle.catalog.api.dto.error.ProductImportError;
import br.com.erp.queenfitstyle.catalog.application.command.ImportProductCommand;
import br.com.erp.queenfitstyle.catalog.application.command.ImportSkuCommand;
import br.com.erp.queenfitstyle.catalog.application.event.ImportErrorEventPublisher;
import br.com.erp.queenfitstyle.catalog.application.exception.ColorNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductDuplicateException;
import br.com.erp.queenfitstyle.catalog.application.service.ProductCodeGenerator;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.port.in.ImportProductsUseCase;
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
public class ImportProductsUseCaseImpl implements ImportProductsUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;
    private final ColorRepositoryPort colorRepository;
    private final ProductCodeGenerator productCodeGenerator;
    private final ImportErrorEventPublisher errorEventPublisher;

    public ImportProductsUseCaseImpl(ProductRepositoryPort productRepository, CategoryRepositoryPort categoryRepository, ColorRepositoryPort colorRepository, ProductCodeGenerator productCodeGenerator, ImportErrorEventPublisher errorEventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.colorRepository = colorRepository;
        this.productCodeGenerator = productCodeGenerator;
        this.errorEventPublisher = errorEventPublisher;
    }

    @Override
    @Transactional
    public List<Product> execute(List<ImportProductCommand> commands) {

        List<Product> savedProducts = new ArrayList<>();
        List<ProductImportError> errors = new ArrayList<>();

        for (ImportProductCommand command : commands) {
            try {
                Category category = validateExistingCategory(command.categoryName());
                validateDuplicateProductEntry(command.name(), category);

                Product product = buildProduct(command, category);
                Set<Sku> skus = new HashSet<>();

                for (ImportSkuCommand skuCmd : command.skus()) {
                    try {
                        skus.add(buildSku(skuCmd, product));
                    } catch (Exception e) {
                        errors.add(new ProductImportError(
                                product.getName(),
                                category.getDisplayName(),
                                "Falha ao criar SKU [Cor: %s, Tamanho: %s]: %s"
                                        .formatted(skuCmd.colorName(), skuCmd.size(), e.getMessage())
                        ));
                    }
                }

                product.addSkus(skus);
                savedProducts.add(productRepository.save(product));

            } catch (Exception e) {
                errors.add(new ProductImportError(
                        command.name(),
                        command.categoryName(),
                        "Falha ao criar Produto: " + e.getMessage()
                ));
            }
        }
        errorEventPublisher.publish(errors);

        return savedProducts;
    }

    private Category validateExistingCategory(String name) {

        CategoryName categoryName = new CategoryName(name);

        return categoryRepository
                .findByNormalizedNameAndActiveTrue(categoryName.normalizedName())
                .orElseThrow(
                        ()-> new CategoryNotFoundException(
                                "Nenhuma categoria encontrada com o nome " + name));

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

    private Sku buildSku(ImportSkuCommand cmd, Product product) {

        Color color = validateExistingColor(cmd.colorName());
        Size size = new Size(cmd.size());
        Price price = new Price(cmd.price());
        Inventory inventory = new Inventory(cmd.inventory());
        SkuCode code = new SkuCode(product.getCode(), color, size);
        return new Sku(code, color, size, price, inventory);
    }

    private Color validateExistingColor(String name) {

        ColorName colorName = new ColorName(name);

        return colorRepository
                .findByNormalizedNameAndActiveTrue(colorName.normalizedName())
                .orElseThrow(
                        ()-> new ColorNotFoundException("Nenhuma cor encontrada com o nome " + name));
    }

    private Product buildProduct(ImportProductCommand command, Category category) {
        ProductCode productCode = productCodeGenerator.nextCode();
        Price productBasePrice = new Price(command.basePrice());

        return new Product(
                command.name(),
                productCode,
                command.description(),
                category,
                productBasePrice
        );
    }
}
