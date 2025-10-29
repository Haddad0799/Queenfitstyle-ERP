package br.com.erp.queenfitstyle.catalog.api.dto.product.request;

import java.util.List;

public record ImportProductsDTO(
        List<ImportProductDTO> products
) { }
