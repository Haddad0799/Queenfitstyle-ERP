package br.com.erp.queenfitstyle.catalog.api.dto.product.request;

import java.math.BigDecimal;

public record UpdateProductDTO(
        String name,
        String description,
        Long categoryId,
        BigDecimal basePrice,
        Boolean active
) {
}
