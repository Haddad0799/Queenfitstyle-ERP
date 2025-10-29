package br.com.erp.queenfitstyle.catalog.application.command;

import java.math.BigDecimal;
import java.util.List;

public record ImportProductCommand(
        String name,
        String description,
        String categoryName,
        BigDecimal basePrice,
        List<ImportSkuCommand> skus
) {}
