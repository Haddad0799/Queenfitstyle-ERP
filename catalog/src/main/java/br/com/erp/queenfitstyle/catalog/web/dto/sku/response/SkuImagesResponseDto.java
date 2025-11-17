package br.com.erp.queenfitstyle.catalog.web.dto.sku.response;

import java.util.List;

public record SkuImagesResponseDto(
        List<SkuImageResponseDto> images
) {
}
