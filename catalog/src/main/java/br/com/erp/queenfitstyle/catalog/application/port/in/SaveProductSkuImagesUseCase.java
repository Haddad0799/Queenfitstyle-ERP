package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.application.command.SaveProductSkuImagesCommand;

public interface SaveProductSkuImagesUseCase {

    void execute(SaveProductSkuImagesCommand commmand);
}
