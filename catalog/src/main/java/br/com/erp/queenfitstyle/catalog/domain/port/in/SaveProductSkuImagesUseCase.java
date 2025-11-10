package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.application.command.SaveProductSkuImagesCommand;

public interface SaveProductSkuImagesUseCase {

    void execute(SaveProductSkuImagesCommand commmand);
}
