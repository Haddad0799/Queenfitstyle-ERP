package br.com.erp.queenfitstyle.catalog.api.dto.product.request;

import br.com.erp.queenfitstyle.catalog.api.dto.sku.request.CreateProductSkuDTO;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductDTO(

        String name,
        String description,
        Long categoryId,
        BigDecimal basePrice,
        List<CreateProductSkuDTO> skus
) {
}
