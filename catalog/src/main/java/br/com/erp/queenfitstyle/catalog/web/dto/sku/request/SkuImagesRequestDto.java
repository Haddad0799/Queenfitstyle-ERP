package br.com.erp.queenfitstyle.catalog.web.dto.sku.request;

public record SkuImagesRequestDto(
        String filename,
        String publicUrl,
        int displayOrder
) {
}
