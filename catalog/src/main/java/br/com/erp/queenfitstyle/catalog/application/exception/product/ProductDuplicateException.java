package br.com.erp.queenfitstyle.catalog.application.exception.product;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import org.springframework.http.HttpStatus;

public class ProductDuplicateException extends ApplicationException {

    public ProductDuplicateException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
