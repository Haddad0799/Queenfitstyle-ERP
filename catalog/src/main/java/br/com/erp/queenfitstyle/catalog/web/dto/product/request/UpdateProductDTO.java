package br.com.erp.queenfitstyle.catalog.web.dto.product.request;

import java.math.BigDecimal;

public record UpdateProductDTO(
        String name,
        String description,
        Long categoryId,
        BigDecimal basePrice,
        Boolean active
) {
}
