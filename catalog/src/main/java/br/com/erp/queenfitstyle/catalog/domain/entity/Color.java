package br.com.erp.queenfitstyle.catalog.domain.entity;

import br.com.erp.queenfitstyle.catalog.domain.valueobject.ColorName;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Hexadecimal;

public class Color {

    private Long id;
    private ColorName name;
    private Hexadecimal hexCode;
    private boolean active;

    public Color(String displayName, Hexadecimal hexCode) {
        this.name = new ColorName(displayName);
        this.hexCode = hexCode;
        this.active = true;
    }

    public Color(Long id, ColorName name, Hexadecimal hexCode, boolean active) {
        this.id = id;
        this.name = name;
        this.hexCode = hexCode;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return name.displayName();
    }

    public String getNormalizedName() {
        return name.normalizedName();
    }

    public String getHexCode() {
        return hexCode.value();
    }

    public boolean isActive() {
        return active;
    }
}
