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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImportProductsUseCaseImpl implements ImportProductsUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;
    private final ColorRepositoryPort colorRepository;
    private final ProductCodeGenerator productCodeGenerator;
    private final ImportErrorEventPublisher errorEventPublisher;

    public ImportProductsUseCaseImpl(
            ProductRepositoryPort productRepository,
            CategoryRepositoryPort categoryRepository,
            ColorRepositoryPort colorRepository,
            ProductCodeGenerator productCodeGenerator,
            ImportErrorEventPublisher errorEventPublisher
    ) {
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

        Map<String, Category> categories = extractExistingCategories(commands);
        Map<String, Color> colors = extractExistingColors(commands);
        Map<String, Product> existingProducts = extractExistingProducts(commands);

        // Set para controlar slugs únicos dentro deste batch
        Set<String> slugsEmLote = new HashSet<>(existingProducts.keySet());

        for (ImportProductCommand command : commands) {
            try {
                Category category = categories.get(new CategoryName(command.categoryName()).normalizedName());

                if (category == null) {
                    throw new CategoryNotFoundException("Categoria não encontrada: " + command.categoryName());
                }

                Slug slug = Slug.from(command.name());

                // 🔹 Verifica duplicado no banco ou no batch
                if (!slugsEmLote.add(slug.value())) {
                    throw new ProductDuplicateException(
                            "Produto duplicado na importação ou já existente: '%s'"
                                    .formatted(command.name())
                    );
                }

                Product product = buildProduct(command, category);

                Set<Sku> skus = new HashSet<>();

                for (ImportSkuCommand skuCmd : command.skus()) {
                    try {
                        Color color = colors.get(new ColorName(skuCmd.colorName()).normalizedName());

                        if (color == null) {
                            throw new ColorNotFoundException("Cor não encontrada: " + skuCmd.colorName());
                        }

                        Sku sku = buildSku(skuCmd, product, color);
                        skus.add(sku);
                    } catch (Exception e) {
                        errors.add(new ProductImportError(
                                command.name(),
                                command.categoryName(),
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


    // --------------------------------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // --------------------------------------------------------------------------------------

    private Map<String, Category> extractExistingCategories(List<ImportProductCommand> commands) {
        Set<String> names = commands.stream()
                .map(cmd -> new CategoryName(cmd.categoryName()).normalizedName())
                .collect(Collectors.toSet());

        return categoryRepository.findByNormalizedNameInAndActiveTrue(names).stream()
                .collect(Collectors.toMap(Category::getNormalizedName, c -> c));
    }

    private Map<String, Color> extractExistingColors(List<ImportProductCommand> commands) {
        Set<String> colorNames = commands.stream()
                .flatMap(cmd -> cmd.skus().stream()
                        .map(sku -> new ColorName(sku.colorName()).normalizedName()))
                .collect(Collectors.toSet());

        return colorRepository.findByNormalizedNameInAndActiveTrue(colorNames).stream()
                .collect(Collectors.toMap(Color::getNormalizedName, c -> c));
    }

    private Map<String, Product> extractExistingProducts(List<ImportProductCommand> commands) {
        Set<String> newSlugs = commands.stream()
                .map(cmd -> Slug.from(cmd.name()).value()) // 👈 converte para String
                .collect(Collectors.toSet());

        return productRepository.findBySlugInAndActiveTrue(newSlugs).stream()
                .collect(Collectors.toMap(Product::getSlug, p -> p));
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

    private Sku buildSku(ImportSkuCommand cmd, Product product, Color color) {
        Size size = new Size(cmd.size());
        Price price = new Price(cmd.price());
        Inventory inventory = new Inventory(cmd.inventory());
        SkuCode code = new SkuCode(product.getCode(), color, size);
        return new Sku(code, color, size, price, inventory);
    }
}

