package br.com.erp.queenfitstyle.catalog.web.dto.color;

public record ColorDetailsDTO(
        Long id,
        String displayName,
        String normalizedName,
        String hexadecimal,
        boolean enable
) {
}
