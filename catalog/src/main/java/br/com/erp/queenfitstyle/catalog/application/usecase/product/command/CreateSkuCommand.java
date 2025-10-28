package br.com.erp.queenfitstyle.catalog.application.usecase.product.command;

import java.math.BigDecimal;

public record CreateSkuCommand(
        String color,
        String size,
        BigDecimal price,
        Integer stock
) { }


