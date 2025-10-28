package br.com.erp.queenfitstyle.catalog.api.dto.sku.response;

import java.math.BigDecimal;

public record SkuDetailsDTO(
        Long id,
        String skuCode,
        String color,
        String size,
        BigDecimal price,
        int inventory,
        Boolean active
) {
}
