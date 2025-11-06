package br.com.erp.queenfitstyle.catalog.web.dto.product.response;

import br.com.erp.queenfitstyle.catalog.web.dto.error.ProductImportError;

import java.util.List;

public record ImportResumeDTO(
        Summary summary,
        List<ProductImportError> errors

) {
}
