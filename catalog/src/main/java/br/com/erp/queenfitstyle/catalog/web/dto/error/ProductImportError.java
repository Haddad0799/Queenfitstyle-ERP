package br.com.erp.queenfitstyle.catalog.web.dto.error;

public record ProductImportError(
        String productName,
        String category,
        String message
) {}