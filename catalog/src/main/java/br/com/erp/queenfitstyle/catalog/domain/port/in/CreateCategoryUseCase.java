package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;

public interface CreateCategoryUseCase {
    Category execute(String name);
}
