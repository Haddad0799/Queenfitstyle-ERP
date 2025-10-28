package br.com.erp.queenfitstyle.catalog.domain.entity;

import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.ProductCode;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Slug;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Product {

    private Long id;
    private final ProductCode productCode;
    private Slug productSlug;
    private String name;
    private String description;
    private Category category;
    private Price basePrice;
    private Boolean active;
    private final Set<Sku> skus = new HashSet<>();

    // 🔹 Construtor principal para criação de novo produto
    public Product(String name, ProductCode productCode, String description, Category category, Price basePrice) {
        this.name = name;
        this.productCode = productCode;
        this.description = description;
        this.category = category;
        this.basePrice = basePrice;
        this.active = true;
        this.productSlug = Slug.from(name);
    }

    // 🔹 Construtor de reidratação (ao carregar do banco)
    public Product(Long id, String name, String description, Category category, Price basePrice, ProductCode productCode, Slug productSlug, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.basePrice = basePrice;
        this.productCode = productCode;
        this.productSlug = productSlug;
        this.active = active;
    }

    // 🔹 Getters
    public Long getId() { return id; }

    public Long getProductCategoryId() { return category.getId(); }

    public String getProductCategoryName() { return category.getDisplayName(); }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getSlug() { return productSlug.value(); }

    public BigDecimal getPrice() { return basePrice.value(); }

    public Category getCategory() { return category; }

    public String getCode() { return productCode.value(); }

    public Boolean isActive() { return active; }

    public Set<Sku> getSkus() { return skus; }

    // 🔹 Métodos de negócio
    public void changeBasePrice(Price newPrice) {
        if (newPrice == null) throw new IllegalArgumentException("O preço base não pode ser nulo");
        this.basePrice = newPrice;
    }

    public void changeCategory(Category newCategory) {
        if (newCategory == null) throw new IllegalArgumentException("A categoria não pode ser nula");
        this.category = newCategory;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void deactivate() { this.active = false; }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio");
        }
        this.name = newName;
        this.productSlug = this.productSlug.updateFrom(newName);
    }

    public void addSku(Sku sku) {
        if (sku == null) throw new IllegalArgumentException("Sku não pode ser nulo");
        this.skus.add(sku);
    }

    public void addSkus(Collection<Sku> skus) {
        if (skus == null || skus.isEmpty()) return;
        skus.forEach(this::addSku);
    }

    public Optional<Sku> findSkuByCode(SkuCode skuCode) {
        if (skuCode == null) {
            throw new IllegalArgumentException("SkuCode não pode ser nulo");
        }
        return skus.stream()
                .filter(sku -> sku.getCode().equalsIgnoreCase(skuCode.value()))
                .findFirst();
    }

}
