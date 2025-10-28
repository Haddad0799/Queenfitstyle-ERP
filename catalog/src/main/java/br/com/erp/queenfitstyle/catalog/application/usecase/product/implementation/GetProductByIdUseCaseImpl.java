package br.com.erp.queenfitstyle.catalog.application.usecase.product.implementation;

import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.port.in.GetProductByIdUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {

    private final ProductRepositoryPort productRepository;

    public GetProductByIdUseCaseImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product execute(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(
                        ()-> new ProductNotFoundException(
                                "Nenhum produto encontrado com o ID fornecido: " + id));
    }
}
