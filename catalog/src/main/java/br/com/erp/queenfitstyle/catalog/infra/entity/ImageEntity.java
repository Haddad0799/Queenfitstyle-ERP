package br.com.erp.queenfitstyle.catalog.infra.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sku_images")
public class ImageEntity {

    @Id
    @Column(length = 36, nullable = false)
    private String uuid;

    @Column(nullable = false, length = 200)
    private String filename;

    @Column(nullable = false, length = 500)
    private String publicUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id", nullable = false)
    private SkuEntity sku;

    // ---------- CONSTRUCTORS ----------
    protected ImageEntity() {}


    public ImageEntity(String uuid,String filename, String publicUrl, Integer displayOrder) {
        this.uuid = uuid;
        this.filename = filename;
        this.publicUrl = publicUrl;
        this.displayOrder = displayOrder;
    }

    // ---------- GETTERS / SETTERS ----------
    public String getUuid() {
        return uuid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public SkuEntity getSku() {
        return sku;
    }

    public void setSku(SkuEntity sku) {
        this.sku = sku;
    }
}
