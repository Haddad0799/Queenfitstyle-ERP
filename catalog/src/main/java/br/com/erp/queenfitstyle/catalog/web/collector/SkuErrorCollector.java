package br.com.erp.queenfitstyle.catalog.web.collector;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SkuErrorCollector {

    private final List<String> errors = new ArrayList<>();

    public void addAll(List<String> list) {
        errors.addAll(list);
    }

    public List<String> getErrors() {
        return List.copyOf(errors);
    }

    public void clear() {
        errors.clear();
    }
}
