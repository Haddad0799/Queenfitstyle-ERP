package br.com.erp.queenfitstyle.catalog.api.dto.sku.request;

import java.math.BigDecimal;

public record UpdateSkuDto(
    BigDecimal price,
    int inventory
) {
}
