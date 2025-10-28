package br.com.erp.queenfitstyle.catalog.api.exception;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @param message   Mensagem principal
 * @param details   Detalhes ou lista de erros
 * @param status    Código HTTP
 * @param timestamp Data/hora do erro
 */
public record ErrorResponse(String message, List<String> details, int status, String timestamp) {
    public ErrorResponse(String message, List<String> details, int status) {
        this(message, details, status, OffsetDateTime.now().toString());
    }

}
