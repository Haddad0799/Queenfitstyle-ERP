package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

public record Hexadecimal(String value) {

    private static final Pattern HEX_PATTERN = Pattern.compile("^#?([A-Fa-f0-9]{6})$");

    public Hexadecimal(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("O código hexadecimal não pode ser nulo ou vazio.");
        }

        var matcher = HEX_PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Código hexadecimal inválido: " + value);
        }

        // Garante que sempre armazene com '#'
        this.value = value.startsWith("#") ? value.toUpperCase() : "#" + value.toUpperCase();
    }

    public String withoutHash() {
        return value.substring(1);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hexadecimal that)) return false;
        return Objects.equals(value, that.value);
    }

}
