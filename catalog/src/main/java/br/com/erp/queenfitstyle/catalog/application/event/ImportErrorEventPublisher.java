package br.com.erp.queenfitstyle.catalog.application.event;

import br.com.erp.queenfitstyle.catalog.api.dto.error.ProductImportError;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportErrorEventPublisher {

    private final ApplicationEventPublisher publisher;

    public ImportErrorEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(List<ProductImportError> errors) {
        if (errors != null && !errors.isEmpty()) {
            publisher.publishEvent(new ImportErrorEvent(errors));
        }
    }
}
