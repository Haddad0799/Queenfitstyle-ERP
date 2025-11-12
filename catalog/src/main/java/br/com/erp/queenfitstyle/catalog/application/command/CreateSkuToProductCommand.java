package br.com.erp.queenfitstyle.catalog.application.command;

import java.math.BigDecimal;

public record CreateSkuToProductCommand(
        Long productId,
        Long colorId,
        String size,
        BigDecimal price,
        Integer inventory
) {
}
