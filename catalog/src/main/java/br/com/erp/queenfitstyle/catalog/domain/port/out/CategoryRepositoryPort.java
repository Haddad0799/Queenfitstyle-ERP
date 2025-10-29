package br.com.erp.queenfitstyle.catalog.domain.port.out;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// Porta de saída (domain/port/out)
public interface CategoryRepositoryPort {
    Category save(Category category);
    boolean existsByNormalizedName(String normalizedName);
    Optional<Category> findById(Long id);
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByActive(Boolean active, Pageable pageable);
    void softDelete(Long id);
    void updateName(Long id ,String newDisplayName, String newNormalizedName);

    boolean existsByNormalizedNameAndNotId(String normalizedName, Long id);
    Optional<Category> findByIdAndActiveTrue(Long id);

    Optional<Category> findByNormalizedNameAndActiveTrue(String name);
}
