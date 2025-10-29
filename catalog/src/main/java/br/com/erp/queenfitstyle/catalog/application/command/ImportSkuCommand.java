package br.com.erp.queenfitstyle.catalog.application.command;

import java.math.BigDecimal;

public record ImportSkuCommand(
        String colorName,
        String size,
        int inventory,
        BigDecimal price
) {}
