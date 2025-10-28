package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.util.Objects;

public record ProductCode(String value) {

    private static final String PREFIX = "QFS";

    public ProductCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("O código do produto não pode ser nulo ou vazio.");
        }
        if (!value.startsWith(PREFIX)) {
            throw new IllegalArgumentException("O código deve começar com o prefixo " + PREFIX);
        }
    }

    public static ProductCode of(String value) {
        return new ProductCode(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCode other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
