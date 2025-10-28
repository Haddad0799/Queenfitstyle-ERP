package br.com.erp.queenfitstyle.catalog.api.exception;

import br.com.erp.queenfitstyle.catalog.application.exception.generic.ApplicationException;
import br.com.erp.queenfitstyle.catalog.domain.exception.generic.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    // Validação de DTO (Bean Validation)
    @ExceptionHandler({MethodArgumentNotValidException.class,
            IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

        ErrorResponse error = new ErrorResponse(
                "Formato inválido",
                details,
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.badRequest().body(error);
    }

    //BusinessExceptions (regras de negócio do domínio)
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(DomainException ex) {
        ErrorResponse error = new ErrorResponse(
                "Violação de regra de negócio",
                List.of(ex.getMessage()),
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    //Erros na camada de aplicação(404,409 etc...)
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Erro de aplicação",
                List.of(ex.getMessage()),
                ex.getStatus().value()
        );
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    //Exceptions inesperadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ex.printStackTrace();

        ErrorResponse error = new ErrorResponse(
                "Erro interno",
                List.of("Ocorreu um erro inesperado no servidor."),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
