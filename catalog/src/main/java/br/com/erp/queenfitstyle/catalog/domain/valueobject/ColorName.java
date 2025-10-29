package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import java.text.Normalizer;
import java.util.Objects;

public class ColorName {

    private final String displayName;
    private final String normalizedName;

    public ColorName(String displayName) {
        this.displayName = capitalize(displayName);
        this.normalizedName = normalize(displayName);
    }

    public ColorName(String displayName, String normalizedName) {
        this.displayName = displayName;
        this.normalizedName = normalizedName;
    }

    private static String normalize(String input) {
        return Normalizer
                .normalize(input.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^A-Za-z0-9]", "_")
                .replaceAll("_+", "_")
                .replaceAll("_$", "")
                .toUpperCase();
    }

    private static String capitalize(String str) {
        String[] parts = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isBlank()) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                        .append(p.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    public String displayName() {
        return displayName;
    }

    public String normalizedName() {
        return normalizedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorName that)) return false;
        return Objects.equals(normalizedName, that.normalizedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
