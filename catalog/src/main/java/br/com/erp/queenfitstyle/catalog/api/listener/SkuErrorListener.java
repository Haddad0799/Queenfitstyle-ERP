package br.com.erp.queenfitstyle.catalog.api.listener;

import br.com.erp.queenfitstyle.catalog.application.event.SkuValidationErrorsEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SkuErrorListener {

    private final List<String> lastErrors = new CopyOnWriteArrayList<>();

    @EventListener
    public void onSkuErrors(SkuValidationErrorsEvent event) {
        lastErrors.clear();
        lastErrors.addAll(event.errors());
    }

    public List<String> getLastErrors() {
        return List.copyOf(lastErrors);
    }
}
