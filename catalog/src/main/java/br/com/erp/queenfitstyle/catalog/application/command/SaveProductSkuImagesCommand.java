package br.com.erp.queenfitstyle.catalog.application.command;

import java.util.List;

public record SaveProductSkuImagesCommand(
        Long productId,
        String skuCode,
        List<CreateImageCommand> imagesCommand
) {
}
