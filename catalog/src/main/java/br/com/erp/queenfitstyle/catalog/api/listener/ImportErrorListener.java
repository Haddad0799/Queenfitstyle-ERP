package br.com.erp.queenfitstyle.catalog.api.listener;

import br.com.erp.queenfitstyle.catalog.api.dto.error.ProductImportError;
import br.com.erp.queenfitstyle.catalog.application.event.ImportErrorEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImportErrorListener {

    private final List<ProductImportError> errors = new ArrayList<>();

    @EventListener
    public void handleImportErrorEvent(ImportErrorEvent event) {
        if (event.errors() != null && !event.errors().isEmpty()) {
            errors.addAll(event.errors());
        }
    }

    public List<ProductImportError> getErrors() {
        return List.copyOf(errors);
    }

    public void clear() {
        errors.clear();
    }
}
