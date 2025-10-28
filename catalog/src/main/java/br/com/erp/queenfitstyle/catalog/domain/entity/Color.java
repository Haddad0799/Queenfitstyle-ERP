package br.com.erp.queenfitstyle.catalog.domain.entity;

public class Color {

    private Long id;
    private String name; //
    private String normalizedName; //
    private String hexCode;
    private boolean active;

    public Color (String name, String hexCode) {
        this.name = normalizeDisplay(name);
        this.normalizedName = normalizeForSearch(name);
        this.active = true;
        this.hexCode = hexCode;
    }

    public Color(Long id, String name, String normalizedName, String hexCode, boolean active) {
        this.id = id;
        this.name = name;
        this.normalizedName = normalizedName;
        this.hexCode = hexCode;
        this.active = active;
    }

    private static String normalizeDisplay(String input) {
        return capitalize(input.toLowerCase().trim());
    }

    private static String normalizeForSearch(String input) {
        return java.text.Normalizer
                .normalize(input.trim(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^A-Za-z0-9]", "_")
                .replaceAll("_+", "_")
                .replaceAll("_$", "")
                .toUpperCase();
    }

    private static String capitalize(String str) {
        String[] parts = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isBlank()) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                        .append(p.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public String getHexCode() {
        return hexCode;
    }

    public boolean isActive() {
        return active;
    }
}

