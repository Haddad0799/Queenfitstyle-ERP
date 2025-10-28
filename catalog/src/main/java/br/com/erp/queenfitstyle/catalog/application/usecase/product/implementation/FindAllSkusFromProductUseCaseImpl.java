package br.com.erp.queenfitstyle.catalog.application.usecase.product.implementation;

import br.com.erp.queenfitstyle.catalog.application.exception.product.ProductNotFoundException;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.port.in.FindAllSkusByProductUseCase;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllSkusFromProductUseCaseImpl implements FindAllSkusByProductUseCase {

    private final ProductRepositoryPort productRepository;

    public FindAllSkusFromProductUseCaseImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Sku> execute(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(
                        "Nenhum produto encontrado para o ID fornecido: " + id));

        return product
                .getSkus()
                .stream()
                .toList();
    }
}
