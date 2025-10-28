package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;

public interface GetCategoryByidUseCase {

    Category execute(Long id);
}
