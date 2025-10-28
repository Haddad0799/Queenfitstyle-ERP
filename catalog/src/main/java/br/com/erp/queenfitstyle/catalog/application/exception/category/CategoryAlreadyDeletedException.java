package br.com.erp.queenfitstyle.catalog.application.exception.category;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import org.springframework.http.HttpStatus;

public class CategoryAlreadyDeletedException extends ApplicationException {
    public CategoryAlreadyDeletedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
