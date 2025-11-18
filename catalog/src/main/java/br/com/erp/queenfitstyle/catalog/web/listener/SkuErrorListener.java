package br.com.erp.queenfitstyle.catalog.web.listener;

import br.com.erp.queenfitstyle.catalog.application.event.SkuValidationErrorsEvent;
import br.com.erp.queenfitstyle.catalog.web.collector.SkuErrorCollector;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SkuErrorListener {

    private final SkuErrorCollector collector;

    public SkuErrorListener(SkuErrorCollector collector) {
        this.collector = collector;
    }

    @EventListener
    public void onSkuErrors(SkuValidationErrorsEvent event) {
        if (event.errors() != null && !event.errors().isEmpty()) {
            collector.addAll(event.errors());
        }
    }
}
