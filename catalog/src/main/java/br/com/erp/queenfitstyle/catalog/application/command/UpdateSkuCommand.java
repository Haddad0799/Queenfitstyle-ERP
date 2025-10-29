package br.com.erp.queenfitstyle.catalog.application.command;

import java.math.BigDecimal;

public record UpdateSkuCommand(
        Long productId,
        String skuCode,
        BigDecimal price,
        int inventory
) {


}
