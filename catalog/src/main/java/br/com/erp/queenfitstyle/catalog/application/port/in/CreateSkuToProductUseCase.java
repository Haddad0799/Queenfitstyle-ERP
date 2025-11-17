package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.application.command.CreateSkuToProductCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;

public interface CreateSkuToProductUseCase {

    Sku execute(CreateSkuToProductCommand command);
}
