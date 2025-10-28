package br.com.erp.queenfitstyle.catalog.application.usecase.category;

import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryAlreadyExistsException;
import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.in.UpdateCategoryNameUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.CategoryName;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryNameUseCaseImpl implements UpdateCategoryNameUseCase {

    private final CategoryRepositoryPort categoryRepository;

    public UpdateCategoryNameUseCaseImpl(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category execute(Long id, String newName) {

        Category category = categoryRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Nenhuma categoria ativa encontrada para o ID fornecido: " + id));

        CategoryName categoryName = new CategoryName(newName);

        category.rename(categoryName);

        // Verifica duplicidade apenas em outras categorias
        if (categoryRepository.existsByNormalizedNameAndNotId(category.getNormalizedName(), id)) {
            throw new CategoryAlreadyExistsException(
                    "Já existe uma categoria com o nome " + category.getNormalizedName());
        }

        categoryRepository.updateName(id, category.getDisplayName(), category.getNormalizedName());

        return category;
    }

}
