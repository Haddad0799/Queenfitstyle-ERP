package br.com.erp.queenfitstyle.catalog.domain.util;

public class NormalizerUtils {
    public static String normalize(String value) {
        if (value == null) return null;
        value = value.trim().toLowerCase();
        value = java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return value;
    }
}
