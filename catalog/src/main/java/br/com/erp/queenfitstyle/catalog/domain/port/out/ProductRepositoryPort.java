package br.com.erp.queenfitstyle.catalog.domain.port.out;

import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    boolean existsByNameAndCategoryId(String normalizedName, Long categoryId);

    Optional<Product> findById(Long id);

    Page<Product> findAllFiltered(Long categoryId, Boolean active, String name, Long colorId, String sizeFilter, Pageable pageable);

    Optional<Product> findBySkuCode(String skuCode);

    Optional<Product> findProductWithSku(Long productId, String skuCode);


}
