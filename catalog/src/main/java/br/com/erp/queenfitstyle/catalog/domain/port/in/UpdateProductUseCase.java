package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.application.command.UpdateProductCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;

public interface UpdateProductUseCase {

    Product execute(UpdateProductCommand command);
}
