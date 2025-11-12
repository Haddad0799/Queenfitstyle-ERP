package br.com.erp.queenfitstyle.catalog.application.command;

import java.util.List;

public record CreateSkusCommand(
        Long productId,
        List<CreateSkuCommand> skuCommands
) {
}
