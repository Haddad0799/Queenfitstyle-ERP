package br.com.erp.queenfitstyle.catalog.infra.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "skus")
public class SkuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku_code", nullable = false, unique = true, length = 50)
    private String skuCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer inventory;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    public SkuEntity() {}

    public SkuEntity(String skuCode, ColorEntity color, String size, BigDecimal price, Integer inventory) {
        this.skuCode = skuCode;
        this.color = color;
        this.size = size;
        this.price = price;
        this.inventory = inventory;
        this.active = true;
    }

    public SkuEntity(Long id ,String skuCode, ColorEntity color, String size, BigDecimal price, Integer inventory, Boolean active) {
        this.id = id;
        this.skuCode = skuCode;
        this.color = color;
        this.size = size;
        this.price = price;
        this.inventory = inventory;
        this.active = active;
    }

    // ---------- GETTERS / SETTERS ----------
    public Long getId() { return id; }

    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public String getSize() {
        return size;
    }

    public ColorEntity getColor() {
        return color;
    }

    public Boolean getActive() {
        return active;
    }

    public void setSize(String size) { this.size = size; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }

    public String getProductcode() { return product.getProductCode(); }
    public void setProduct(ProductEntity product) { this.product = product; }

    public Boolean isActive() {
        return active;
    }
}
