package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.CreateProductCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;

public interface CreateProductUseCase {
    Product execute(CreateProductCommand command);
}
