package br.com.erp.queenfitstyle.catalog.web.dto.sku.request;

import java.math.BigDecimal;

public record CreateProductSkuDTO(
        Long colorId,
        String size,
        int inventory,
        BigDecimal price
) {
}
