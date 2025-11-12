package br.com.erp.queenfitstyle.catalog.web.dto.sku.request;

import java.util.List;

public record SaveSkuImagesDTO(
        List<SkuImagesRequestDto> images
) {
}
