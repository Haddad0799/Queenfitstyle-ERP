package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.util.Objects;

public record Size(String value) {

    public Size {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tamanho inválido");
        }
        value = value.trim().toUpperCase();
    }

    public static Size of(String value) {
        return new Size(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Size other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
