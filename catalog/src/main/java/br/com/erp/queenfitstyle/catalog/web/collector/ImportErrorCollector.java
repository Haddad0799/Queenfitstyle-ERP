package br.com.erp.queenfitstyle.catalog.web.collector;

import br.com.erp.queenfitstyle.catalog.web.dto.error.ProductImportError;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ImportErrorCollector {

    private final List<ProductImportError> errors = new ArrayList<>();

    public void addAll(List<ProductImportError> list) {
        errors.addAll(list);
    }

    public List<ProductImportError> getErrors() {
        return List.copyOf(errors);
    }

    public void clear() {
        errors.clear();
    }
}
