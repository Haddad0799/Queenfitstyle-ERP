package br.com.erp.queenfitstyle.catalog.api.mapper;

import br.com.erp.queenfitstyle.catalog.api.dto.error.Error;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.CreateProductResumeDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.ProductCategoryDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.response.ProductDetailsDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.sku.response.SkuDetailsDTO;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {} // impede instância

    public static ProductDetailsDTO toDetailsDTO(Product product) {
        Category category = product.getCategory();
        return new ProductDetailsDTO(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getCode(),
                product.getDescription(),
                new ProductCategoryDTO(category.getId(), category.getDisplayName()),
                product.getPrice(),
                product.isActive()
        );
    }

    public static CreateProductResumeDTO toResumeDTO(Product product, List<String> errors) {
        ProductDetailsDTO details = toDetailsDTO(product);
       List<Error> errorDtos = errors.stream().map(Error::new).toList();
        return new CreateProductResumeDTO(details, errorDtos.size(), errorDtos);
    }

    public static List<SkuDetailsDTO> toSkuDetailsDTO(Collection<Sku> skus) {
        if (skus == null || skus.isEmpty()) {
            return List.of();
        }

        return skus.stream()
                .map(sku -> new SkuDetailsDTO(
                        sku.getId(),
                        sku.getCode(),
                        sku.getColor(),
                        sku.getSize(),
                        sku.getPrice(),
                        sku.getInventory(),
                        sku.isActive()
                ))
                .toList();
    }


    public static SkuDetailsDTO toSkuDetailsDTO(Sku sku) {
        if (sku == null) return null;

        return new SkuDetailsDTO(
                sku.getId(),
                sku.getCode(),
                sku.getColor(),
                sku.getSize(),
                sku.getPrice(),
                sku.getInventory(),
                sku.isActive()
        );
    }

}
