package br.com.erp.queenfitstyle.catalog.domain.valueobject;

public record Inventory(int value) {

    public Inventory {
        if (value <= 0) {
            throw new IllegalArgumentException("Não é possível cadastrar um produto sem estoque ou com estoque negativado.");
        }
    }

    public static Inventory of(int value) {
        return new Inventory(value);
    }

    public Inventory increase(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade para adicionar deve ser positiva.");
        }
        return new Inventory(this.value + quantity);
    }

    public Inventory decrease(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade para remover deve ser positiva.");
        }
        if (this.value - quantity < 0) {
            throw new IllegalStateException("Estoque insuficiente para a operação.");
        }
        return new Inventory(this.value - quantity);
    }

    public boolean isAvailable() {
        return value > 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
