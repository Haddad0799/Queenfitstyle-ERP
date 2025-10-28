package br.com.erp.queenfitstyle.catalog.domain.entity;

import br.com.erp.queenfitstyle.catalog.domain.valueobject.Inventory;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Size;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;

import java.math.BigDecimal;

public class Sku {

    private Long id;
    private final SkuCode code;
    private final Color color;
    private final Size size;
    private Price price;
    private boolean active;
    private Inventory inventory;


    public Sku(SkuCode code, Color color, Size size, Price price, Inventory inventory) {
        this.code = code;
        this.color = color;
        this.size = size;
        this.price = price;
        this.active = true;
        this.inventory = inventory;
    }

    public Sku(Long id, SkuCode code, Color color, Size size, Price price, boolean active, Inventory inventory) {
        this.id = id;
        this.code = code;
        this.color = color;
        this.size = size;
        this.price = price;
        this.active = active;
        this.inventory = inventory;
    }


    public Long getId() {
        return id;
    }

    public String getCode() {
        return code.value();
    }

    public Color getColor() {
        return color;
    }

    public String getSize() {
        return size.value();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public boolean isActive() {
        return active;
    }

    public int getInventory() {
        return inventory.value();
    }

    public String getColorName() {
        return color.getName();
    }

    public void changeInventory(Inventory newInventory) {
        this.inventory = newInventory;
    }

    public void changePrice(Price newPrice) {
        this.price = newPrice;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sku sku)) return false;
        // Compara pelo valor do code
        return this.getCode().equals(sku.getCode());
    }

    @Override
    public int hashCode() {
        // Usa o valor do code
        return this.getCode().hashCode();
    }



}
