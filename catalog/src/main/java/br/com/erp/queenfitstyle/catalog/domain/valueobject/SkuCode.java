package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.util.Objects;

public record SkuCode(String value) {

    public SkuCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("SkuCode não pode ser nulo ou vazio");
        }
    }

    public SkuCode(ProductCode productCode, Color color, Size size) {
        this(buildSkuValue(productCode, color, size));
    }

    private static String buildSkuValue(ProductCode productCode, Color color, Size size) {
        if (productCode == null) throw new IllegalArgumentException("ProductCode não pode ser nulo");
        if (color == null) throw new IllegalArgumentException("Color não pode ser nula");
        if (size == null) throw new IllegalArgumentException("Size não pode ser nulo");

        String normalizedColor = normalize(color.value());
        String normalizedSize = normalize(size.value());

        return productCode.value() + "-" + normalizedColor + "-" + normalizedSize;
    }

    private static String normalize(String value) {
        if (value == null) return "";
        return java.text.Normalizer
                .normalize(value.trim(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^A-Za-z0-9]", "_")
                .replaceAll("_+", "_")
                .replaceAll("_$", "")
                .toUpperCase();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkuCode other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
