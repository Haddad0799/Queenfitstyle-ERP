package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;

public interface UpdateCategoryNameUseCase {

    Category execute(Long id, String name);
}
