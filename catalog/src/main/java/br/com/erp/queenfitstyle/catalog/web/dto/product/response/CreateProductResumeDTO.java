package br.com.erp.queenfitstyle.catalog.web.dto.product.response;

import br.com.erp.queenfitstyle.catalog.web.dto.error.Error;

import java.util.List;

public record CreateProductResumeDTO(
        ProductDetailsDTO product,
        int totalErrors,
        List<Error> errors
) {
}
