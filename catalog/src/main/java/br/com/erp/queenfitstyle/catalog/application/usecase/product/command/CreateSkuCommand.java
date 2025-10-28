package br.com.erp.queenfitstyle.catalog.application.usecase.product.command;

import java.math.BigDecimal;

public record CreateSkuCommand(
        Long colorId,
        String size,
        BigDecimal price,
        Integer inventory
) { }


