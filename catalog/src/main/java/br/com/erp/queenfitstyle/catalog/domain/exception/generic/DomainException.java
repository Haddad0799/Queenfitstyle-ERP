package br.com.erp.queenfitstyle.catalog.domain.exception.generic;

public abstract class DomainException extends RuntimeException {
    protected DomainException(String message) {
        super(message);
    }
}
