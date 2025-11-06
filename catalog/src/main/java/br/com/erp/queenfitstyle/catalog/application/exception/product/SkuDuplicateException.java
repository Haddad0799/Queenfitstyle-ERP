package br.com.erp.queenfitstyle.catalog.application.exception.product;

import br.com.erp.queenfitstyle.catalog.domain.exception.generic.DomainException;

public class SkuDuplicateException extends DomainException {
    public SkuDuplicateException(String message) {
        super(message);
    }
}
