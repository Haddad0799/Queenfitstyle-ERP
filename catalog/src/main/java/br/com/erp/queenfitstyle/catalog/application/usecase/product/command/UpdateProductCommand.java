package br.com.erp.queenfitstyle.catalog.application.usecase.product.command;

import java.math.BigDecimal;

public record UpdateProductCommand(
        Long productId,
        String name,
        String description,
        Long categoryId,
        BigDecimal basePrice,
        Boolean active
) {}
