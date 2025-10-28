package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.text.Normalizer;
import java.util.Objects;

public record Slug(String value) {

    public static Slug from(String productName) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio para gerar o slug.");
        }

        String normalized = Normalizer.normalize(productName, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]+", "") // remove acentos
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // remove caracteres especiais
                .trim()
                .replaceAll("\\s+", "-"); // substitui espaços por '-'

        return new Slug(normalized);
    }

    public Slug updateFrom(String newProductName) {
        return from(newProductName);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slug slug)) return false;
        return Objects.equals(value, slug.value);
    }

}
