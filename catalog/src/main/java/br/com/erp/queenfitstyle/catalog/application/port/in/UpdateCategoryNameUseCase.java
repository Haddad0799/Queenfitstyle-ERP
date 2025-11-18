package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;

public interface UpdateCategoryNameUseCase {

    Category execute(Long id, String name);
}
