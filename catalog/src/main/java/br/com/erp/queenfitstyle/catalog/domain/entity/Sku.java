package br.com.erp.queenfitstyle.catalog.domain.entity;

import br.com.erp.queenfitstyle.catalog.domain.valueobject.Inventory;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Size;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Sku {

    private Long id;
    private final SkuCode code;
    private final Color color;
    private final Size size;
    private Price price;
    private boolean active;
    private Inventory inventory;
    private final List<Image> images = new ArrayList<>();


    public Sku(SkuCode code, Color color, Size size, Price price, Inventory inventory) {
        this.code = code;
        this.color = color;
        this.size = size;
        this.price = price;
        this.active = false;
        this.inventory = inventory;
    }

    public Sku(Long id, SkuCode code, Color color, Size size, Price price,
               boolean active, Inventory inventory, List<Image> images) {
        this.id = id;
        this.code = code;
        this.color = color;
        this.size = size;
        this.price = price;
        this.active = active;
        this.inventory = inventory;

        if (images != null) {
            this.images.addAll(images);
        }
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
        return color.getDisplayName();
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

    public void addImage(Image image) {
        if (images.size() >= 3)
            throw new IllegalStateException("Um SKU pode ter no máximo 3 imagens.");

        boolean orderExists = images.stream()
                .anyMatch(img -> img.getDisplayOrder() == image.getDisplayOrder());
        if (orderExists)
            throw new IllegalStateException("Já existe uma imagem com essa ordem de exibição.");

        images.add(image);
    }

    public List<Image> getImages() {
        return List.copyOf(images);
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
        return this.getCode().hashCode();
    }

}
