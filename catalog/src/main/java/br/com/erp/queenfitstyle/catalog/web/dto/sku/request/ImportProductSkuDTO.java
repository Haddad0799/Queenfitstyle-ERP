package br.com.erp.queenfitstyle.catalog.web.dto.sku.request;

import java.math.BigDecimal;

public record ImportProductSkuDTO(
        String colorName,
        String size,
        int inventory,
        BigDecimal price
) { }
