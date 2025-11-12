package br.com.erp.queenfitstyle.catalog.web.dto.product.response;

import br.com.erp.queenfitstyle.catalog.web.dto.sku.response.SkuDetailsDTO;

import java.util.List;

public record ProductSkusDTO(

        List<SkuDetailsDTO> skus
) {
}
