package br.com.erp.queenfitstyle.catalog.application.usecase.product.implementation;

import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.port.in.FindAllProductsFilteredUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindAllProductsFilteredUseCaseImpl implements FindAllProductsFilteredUseCase {

    private final ProductRepositoryPort productRepository;

    public FindAllProductsFilteredUseCaseImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Page<Product> execute(Long categoryId, Boolean active, String name, Long colorId, String sizeFilter, Pageable pageable) {
        return productRepository.findAllFiltered(categoryId,active,name,colorId, sizeFilter, pageable);
    }
}
