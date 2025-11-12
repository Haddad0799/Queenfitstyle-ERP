package br.com.erp.queenfitstyle.catalog.web.dto.product.response;

public record Summary(
        int totalProducts,
        int successfulProducts,
        int failedProducts,
        int totalSkus,
        int successfulSkus,
        int failedSkus
) {}