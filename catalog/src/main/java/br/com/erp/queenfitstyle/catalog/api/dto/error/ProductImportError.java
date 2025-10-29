package br.com.erp.queenfitstyle.catalog.api.dto.error;

public record ProductImportError(
        String productName,
        String category,
        String message
) {}