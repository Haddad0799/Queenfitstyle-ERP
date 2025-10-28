package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllProductsFilteredUseCase {

    Page<Product> execute(Long categoryId, Boolean active, String name, Long colorId, String sizeFilter, Pageable pageable);

}
