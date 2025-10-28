package br.com.erp.queenfitstyle.catalog.application.exception.category;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsException extends ApplicationException {
    public CategoryAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
