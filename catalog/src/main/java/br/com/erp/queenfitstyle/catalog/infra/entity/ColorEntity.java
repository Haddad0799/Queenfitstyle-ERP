package br.com.erp.queenfitstyle.catalog.infra.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "colors")
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String normalizedName;

    @Column(nullable = false, unique = true)
    private String hexaCode;

    @Column(nullable = false)
    private boolean active;

    // construtor vazio para JPA
    protected ColorEntity() {}

     public ColorEntity(String name, String normalizedName, String hexaCode, boolean active) {
        this.name = name;
        this.normalizedName = normalizedName;
        this.hexaCode = hexaCode;
        this.active = active;
    }

    public ColorEntity(Long id, String name, String normalizedName, String hexaCode, boolean active) {
        this.id = id;
        this.name = name;
        this.normalizedName = normalizedName;
        this.hexaCode = hexaCode;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getHexaCode() {
        return hexaCode;
    }

    public String getName() {
        return name;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public boolean isActive() {
        return active;
    }
}
