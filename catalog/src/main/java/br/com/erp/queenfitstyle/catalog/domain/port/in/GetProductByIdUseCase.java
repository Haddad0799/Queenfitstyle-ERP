package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Product;

public interface GetProductByIdUseCase {

    Product execute(Long id);
}
