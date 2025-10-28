package br.com.erp.queenfitstyle.catalog.infra.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "product_code", nullable = false, unique = true, length = 20)
    private String productCode;

    @Column(nullable = false, unique = true)
    private String slug; // 🔹 novo campo para o slug

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<SkuEntity> skus = new HashSet<>();

    public ProductEntity() {}

    public ProductEntity(String name, String description, CategoryEntity category,
                         BigDecimal basePrice, String productCode, String slug, Boolean active) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.basePrice = basePrice;
        this.productCode = productCode;
        this.slug = slug;
        this.active = active;
    }

    public ProductEntity(Long id, String name, String description, CategoryEntity category,
                         BigDecimal basePrice, String productCode, String slug, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.basePrice = basePrice;
        this.productCode = productCode;
        this.slug = slug;
        this.active = active;
    }

    // ---------- RELACIONAMENTO ----------
    public void addSku(SkuEntity sku) {
        sku.setProduct(this);
        this.skus.add(sku);
    }

    public void addSkus(Set<SkuEntity> skus) {
        skus.forEach(this::addSku);
    }

    public void removeSku(SkuEntity sku) {
        this.skus.remove(sku);
        sku.setProduct(null);
    }

    // ---------- GETTERS / SETTERS ----------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) { this.category = category; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public Boolean isActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Set<SkuEntity> getSkus() { return skus; }
    public void setSkus(Set<SkuEntity> skus) { this.skus = skus; }
}
