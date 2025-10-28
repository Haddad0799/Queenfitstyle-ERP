package br.com.erp.queenfitstyle.catalog.domain.exception.category;

import br.com.erp.queenfitstyle.catalog.domain.exception.generic.DomainException;

public class InvalidCategoryNameException extends DomainException {
    public InvalidCategoryNameException(String message) {
        super(message);
    }
}
