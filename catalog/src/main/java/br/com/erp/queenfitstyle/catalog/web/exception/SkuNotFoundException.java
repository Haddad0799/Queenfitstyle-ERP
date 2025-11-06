package br.com.erp.queenfitstyle.catalog.web.exception;

public class SkuNotFoundException extends RuntimeException{
    public SkuNotFoundException(String message) {
        super(message);
    }
}
