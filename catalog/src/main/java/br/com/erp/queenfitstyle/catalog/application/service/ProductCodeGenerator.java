package br.com.erp.queenfitstyle.catalog.application.service;

import br.com.erp.queenfitstyle.catalog.domain.valueobject.ProductCode;

public interface ProductCodeGenerator {
    ProductCode nextCode();
}
