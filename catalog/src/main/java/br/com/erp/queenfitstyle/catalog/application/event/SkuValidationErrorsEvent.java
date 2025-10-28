package br.com.erp.queenfitstyle.catalog.application.event;

import java.util.List;

public record SkuValidationErrorsEvent(List<String> errors) {}
