package br.com.erp.queenfitstyle.catalog.application.usecase.category;

import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.in.GetCategoryByidUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryByidUseCaseImpl implements GetCategoryByidUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public GetCategoryByidUseCaseImpl(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category execute(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(
                        "Nenhuma categoria encontrada para o ID fornecido: " + id));
    }
}
