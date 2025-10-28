package br.com.erp.queenfitstyle.catalog.application.usecase.product.command;

import java.math.BigDecimal;

public record UpdateSkuCommand(
        Long productId,
        String skuCode,
        BigDecimal price,
        int inventory
) {


}
