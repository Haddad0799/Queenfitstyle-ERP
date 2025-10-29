package br.com.erp.queenfitstyle.catalog.application.command;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductCommand(
        String name,
        String description,
        Long categoryId,
        BigDecimal basePrice,
        List<CreateSkuCommand> skus
) { }
