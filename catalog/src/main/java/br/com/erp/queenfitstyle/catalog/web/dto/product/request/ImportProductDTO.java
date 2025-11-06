package br.com.erp.queenfitstyle.catalog.web.dto.product.request;

import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.ImportProductSkuDTO;
import java.math.BigDecimal;
import java.util.List;

public record ImportProductDTO(
        String name,
        String description,
        String categoryName,
        BigDecimal basePrice,
        List<ImportProductSkuDTO> skus
) { }
