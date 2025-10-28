package br.com.erp.queenfitstyle.catalog.application.usecase.category;

import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryAlreadyExistsException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.in.CreateCategoryUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public CreateCategoryUseCaseImpl(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category execute(String name) {

        Category category = new Category(name);

        if(categoryRepository.existsByNormalizedName(category.getNormalizedName())) {
            throw new CategoryAlreadyExistsException(
                    "Já existe uma categoria com o nome " + category.getNormalizedName());
        }

        return categoryRepository.save(category);
    }


}
