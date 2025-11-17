package br.com.erp.queenfitstyle.catalog.web.dto.sku.response;

public record SkuImageResponseDto(
        String uuid,
        String publicUrl,
        int displayOrder
) {
}
