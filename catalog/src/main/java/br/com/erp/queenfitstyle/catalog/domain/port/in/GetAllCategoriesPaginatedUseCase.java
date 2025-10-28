package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllCategoriesPaginatedUseCase {

    Page<Category> execute(Pageable pageable, Boolean active);
}
