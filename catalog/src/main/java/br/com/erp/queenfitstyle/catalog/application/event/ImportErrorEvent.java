package br.com.erp.queenfitstyle.catalog.application.event;

import br.com.erp.queenfitstyle.catalog.api.dto.error.ProductImportError;
import java.util.List;

public record ImportErrorEvent(List<ProductImportError> errors) { }
