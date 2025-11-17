package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;

public interface GetCategoryByidUseCase {

    Category execute(Long id);
}
