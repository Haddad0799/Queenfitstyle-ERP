package br.com.erp.queenfitstyle.catalog.api.dto.product.response;

import br.com.erp.queenfitstyle.catalog.api.dto.sku.response.SkuDetailsDTO;

import java.util.List;

public record ProductSkusDTO(

        List<SkuDetailsDTO> skus
) {
}
