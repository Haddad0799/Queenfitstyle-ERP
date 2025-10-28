package br.com.erp.queenfitstyle.catalog.api.exception;

public class SkuNotFoundException extends RuntimeException{
    public SkuNotFoundException(String message) {
        super(message);
    }
}
