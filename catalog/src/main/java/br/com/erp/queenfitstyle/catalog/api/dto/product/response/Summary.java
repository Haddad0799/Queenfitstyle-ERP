package br.com.erp.queenfitstyle.catalog.api.dto.product.response;

public record Summary(
        int totalProducts,
        int successfulProducts,
        int failedProducts,
        int totalSkus,
        int successfulSkus,
        int failedSkus
) {}