package br.com.erp.queenfitstyle.catalog.api.dto.product.request;

import br.com.erp.queenfitstyle.catalog.api.dto.sku.request.ImportProductSkuDTO;
import java.math.BigDecimal;
import java.util.List;

public record ImportProductDTO(
        String name,
        String description,
        String categoryName,
        BigDecimal basePrice,
        List<ImportProductSkuDTO> skus
) { }
