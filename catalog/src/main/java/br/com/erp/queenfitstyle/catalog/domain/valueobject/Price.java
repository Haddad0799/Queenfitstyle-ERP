package br.com.erp.queenfitstyle.catalog.domain.valueobject;


import java.math.BigDecimal;
import java.math.RoundingMode;

public record Price(BigDecimal value) {

    public Price(BigDecimal value) {
        // Verifica se é nulo
        if (value == null) {
            throw new IllegalArgumentException("O preço não pode ser nulo");
        }

        // Verifica se é maior que zero
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero");
        }

        // Ajusta para 2 casas decimais
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    // Igualdade baseada no valor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price price)) return false;
        return value.equals(price.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

