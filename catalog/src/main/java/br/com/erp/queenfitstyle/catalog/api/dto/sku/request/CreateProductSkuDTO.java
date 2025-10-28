package br.com.erp.queenfitstyle.catalog.api.dto.sku.request;

import java.math.BigDecimal;

public record CreateProductSkuDTO(
        String color,
        String size,
        int inventory,
        BigDecimal price
) {
}
