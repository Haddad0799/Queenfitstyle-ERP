package br.com.erp.queenfitstyle.catalog.infra.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String displayName;


    private String normalizedName;

    @Column(nullable = false)
    private Boolean active;


    public CategoryEntity() {}

    public CategoryEntity(String displayName, String normalizedName, Boolean active) {
        this.displayName = displayName;
        this.normalizedName = normalizedName;
        this.active = active;
    }

    public CategoryEntity(Long id, String displayName, String normalizedName, Boolean active) {
        this.id = id;
        this.displayName = displayName;
        this.normalizedName = normalizedName;
        this.active = active;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getNormalizedName() { return normalizedName; }

    public void setNormalizedName(String normalizedName) { this.normalizedName = normalizedName; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }
}
