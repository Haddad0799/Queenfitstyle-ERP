package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.util.Objects;

public record Color(String value) {

    public Color {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Cor inválida");
        }
        value = value.trim().toLowerCase();
    }

    public static Color of(String value) {
        return new Color(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
