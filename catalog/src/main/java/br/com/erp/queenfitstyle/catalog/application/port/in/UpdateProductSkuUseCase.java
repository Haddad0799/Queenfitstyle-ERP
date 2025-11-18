package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.application.command.UpdateSkuCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;

public interface UpdateProductSkuUseCase {

    Product execute(UpdateSkuCommand command);
}
