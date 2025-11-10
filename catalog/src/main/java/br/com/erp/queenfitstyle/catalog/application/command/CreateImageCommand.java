package br.com.erp.queenfitstyle.catalog.application.command;

public record CreateImageCommand(
        String filename,
        String publicUrl,
        int displayOrder
) {
}
