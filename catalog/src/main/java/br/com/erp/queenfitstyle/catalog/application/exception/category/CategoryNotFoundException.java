package br.com.erp.queenfitstyle.catalog.application.exception.category;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends ApplicationException {
    public CategoryNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
