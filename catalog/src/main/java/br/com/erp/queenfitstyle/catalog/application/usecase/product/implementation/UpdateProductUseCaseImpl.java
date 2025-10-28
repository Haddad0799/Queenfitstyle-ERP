package br.com.erp.queenfitstyle.catalog.application.usecase.product.implementation;

import br.com.erp.queenfitstyle.catalog.application.exception.category.CategoryNotFoundException;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.UpdateProductCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.port.in.UpdateProductUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    public UpdateProductUseCaseImpl(ProductRepositoryPort productRepository, CategoryRepositoryPort categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Product execute(UpdateProductCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Delegando a atualização para o domínio
        if (command.name() != null) product.rename(command.name());

        if (command.description() != null) product.changeDescription(command.description());

        if (command.categoryId() != null) {
            Category newCategory = categoryRepository
                    .findById(command.categoryId())
                    .orElseThrow(()-> new CategoryNotFoundException(
                            "Nenhuma categoria encontrada para o ID fornecido: " + command.categoryId()));

            product.changeCategory(newCategory);
        }

        if (command.basePrice() != null) product.changeBasePrice(new Price(command.basePrice()));

        return productRepository.save(product);
    }
}
