package br.com.erp.queenfitstyle.catalog.api.dto.category.response;

public record CategoryCreatedDTO(
        Long id,
        String displayName,
        String normalizedName,
        Boolean enabled
) {
}
