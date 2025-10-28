package br.com.erp.queenfitstyle.catalog.application.exception.product;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ApplicationException {

    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
