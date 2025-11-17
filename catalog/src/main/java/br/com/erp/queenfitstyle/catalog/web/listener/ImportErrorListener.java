package br.com.erp.queenfitstyle.catalog.web.listener;

import br.com.erp.queenfitstyle.catalog.application.event.ImportErrorEvent;
import br.com.erp.queenfitstyle.catalog.web.collector.ImportErrorCollector;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ImportErrorListener {

    private final ImportErrorCollector collector;

    public ImportErrorListener(ImportErrorCollector collector) {
        this.collector = collector;
    }

    @EventListener
    public void handleImportErrorEvent(ImportErrorEvent event) {
        if (event.errors() != null && !event.errors().isEmpty()) {
            collector.addAll(event.errors());
        }
    }
}
