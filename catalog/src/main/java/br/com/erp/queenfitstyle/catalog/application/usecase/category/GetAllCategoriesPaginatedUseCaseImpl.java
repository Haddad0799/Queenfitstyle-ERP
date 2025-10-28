package br.com.erp.queenfitstyle.catalog.application.usecase.category;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.in.GetAllCategoriesPaginatedUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllCategoriesPaginatedUseCaseImpl implements GetAllCategoriesPaginatedUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public GetAllCategoriesPaginatedUseCaseImpl(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Page<Category> execute(Pageable pageable, Boolean active) {

        return (active == null)
                ? categoryRepository.findAll(pageable)
                : categoryRepository.findByActive(active, pageable);
    }

}
