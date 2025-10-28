package br.com.erp.queenfitstyle.catalog.application.usecase.category;

import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryAlreadyDeletedException;
import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.in.DeleteCategoryUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

   private final CategoryRepositoryPort categoryRepository;

    public DeleteCategoryUseCaseImpl(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(
                        "Nenhuma categoria encontrada para o ID fornecido: " + id));

        if(!category.isActive()) {
            throw new CategoryAlreadyDeletedException("Categoria já foi excluída.");
        }

        categoryRepository.softDelete(id);

    }
}
