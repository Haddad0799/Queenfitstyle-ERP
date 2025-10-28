package br.com.erp.queenfitstyle.catalog.domain.valueobject;

import br.com.erp.queenfitstyle.catalog.domain.exception.category.InvalidCategoryNameException;
import br.com.erp.queenfitstyle.catalog.domain.util.NormalizerUtils;

import java.util.Objects;

public class CategoryName {

    private final String displayName;
    private final String normalizedName;

    public CategoryName(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidCategoryNameException("O nome da categoria não pode ser vazio.");
        }

        if (!value.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) {
            throw new InvalidCategoryNameException("O nome da categoria deve conter apenas letras e espaços.");
        }

        this.displayName = value.trim();
        this.normalizedName = NormalizerUtils.normalize(value);
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
        if (!(o instanceof CategoryName that)) return false;
        return normalizedName.equals(that.normalizedName);
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
