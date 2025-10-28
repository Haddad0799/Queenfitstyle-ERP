package br.com.erp.queenfitstyle.catalog.domain.entity;

import br.com.erp.queenfitstyle.catalog.domain.valueobject.CategoryName;

public class Category {

    private Long id;
    private CategoryName name;
    private Boolean active;

    public Category(String displayName) {
        this.name = new CategoryName(displayName);
        this.active = true;
    }

    public Category(Long id, String displayName, Boolean active) {
        this.id = id;
        this.name = new CategoryName(displayName);
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

    public Boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void rename(CategoryName newName) {

        // Evita renomear para o mesmo nome normalizado (ex: "Camisetas" → "camisetas")
        if (this.getNormalizedName().equals(newName.normalizedName())) {
            // Porém, se apenas o displayName mudou (ex: "camisetas" → "Camisetas"), permite
            if (!this.getDisplayName().equals(newName.displayName())) {
                this.name = newName; // atualiza apenas o display name
            }
            return; // não faz nada se for exatamente o mesmo nome
        }

        // Nome realmente diferente — aplica a mudança
        this.name = newName;
    }

}
