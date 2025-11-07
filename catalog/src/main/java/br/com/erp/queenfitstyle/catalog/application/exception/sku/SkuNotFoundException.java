package br.com.erp.queenfitstyle.catalog.application.exception.sku;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import org.springframework.http.HttpStatus;

public class SkuNotFoundException extends ApplicationException {
    public SkuNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
