package br.com.erp.queenfitstyle.catalog.api.dto.product.response;

import br.com.erp.queenfitstyle.catalog.api.dto.error.Error;

import java.util.List;

public record CreateProductResumeDTO(
        ProductDetailsDTO product,
        int totalErrors,
        List<Error> errors
) {
}
