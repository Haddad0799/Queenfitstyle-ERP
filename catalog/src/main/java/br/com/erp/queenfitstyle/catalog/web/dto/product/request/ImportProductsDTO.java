package br.com.erp.queenfitstyle.catalog.web.dto.product.request;

import java.util.List;

public record ImportProductsDTO(
        List<ImportProductDTO> products
) { }
