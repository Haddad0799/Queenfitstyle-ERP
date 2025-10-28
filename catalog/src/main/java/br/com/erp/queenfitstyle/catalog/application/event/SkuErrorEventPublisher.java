package br.com.erp.queenfitstyle.catalog.application.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuErrorEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SkuErrorEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(List<String> errors) {
        if (errors != null && !errors.isEmpty()) {
            publisher.publishEvent(new SkuValidationErrorsEvent(errors));
        }
    }
}
