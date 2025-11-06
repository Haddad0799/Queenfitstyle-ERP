package br.com.erp.queenfitstyle.catalog.web.dto.product.response;

import java.math.BigDecimal;

public record ProductDetailsDTO(
        Long id,
        String name,
        String slug,
        String code,
        String description,
        ProductCategoryDTO category,
        BigDecimal price,
        Boolean enabled

) {
}
