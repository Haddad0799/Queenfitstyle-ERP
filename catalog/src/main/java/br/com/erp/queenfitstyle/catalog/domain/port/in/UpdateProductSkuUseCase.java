package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.UpdateSkuCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;

public interface UpdateProductSkuUseCase {

    Product execute(UpdateSkuCommand command);
}
