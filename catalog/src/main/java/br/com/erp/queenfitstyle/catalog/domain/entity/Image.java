package br.com.erp.queenfitstyle.catalog.domain.entity;

import java.util.UUID;

public class Image {

    private final UUID id;
    private final String filename;
    private final String publicUrl;
    private int displayOrder;

    public Image(UUID id, String filename, String publicUrl, int displayOrder) {
        this.id = id;
        this.filename = filename;
        this.publicUrl = publicUrl;
        this.displayOrder = displayOrder;
    }

    public static Image create(String filename, String publicUrl, int displayOrder){
        return new Image(UUID.randomUUID(), filename, publicUrl, displayOrder);
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public String getFilename() {
        return filename;
    }

    public UUID getId() {
        return id;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }
}
