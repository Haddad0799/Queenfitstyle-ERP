package br.com.erp.queenfitstyle.catalog.infra.adapter.repository;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.domain.port.out.CategoryRepositoryPort;
import br.com.erp.queenfitstyle.catalog.infra.entity.CategoryEntity;
import br.com.erp.queenfitstyle.catalog.infra.mapper.CategoryEntityMapper;
import br.com.erp.queenfitstyle.catalog.infra.repository.CategoryJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryAdapter(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category save(Category category) {

        CategoryEntity entity = categoryJpaRepository.save(CategoryEntityMapper.toNewEntity(category));

        return CategoryEntityMapper.toDomain(entity);
    }

    @Override
    public boolean existsByNormalizedName(String normalizedName) {
        return categoryJpaRepository.existsByNormalizedName(normalizedName);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository
                .findById(id)
                .map(CategoryEntityMapper::toDomain);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryJpaRepository
                .findAll(pageable)
                .map(CategoryEntityMapper::toDomain);
    }

    @Override
    public Page<Category> findByActive(Boolean active, Pageable pageable) {
        return categoryJpaRepository
                .findAllByActive(active, pageable)
                .map(CategoryEntityMapper::toDomain);
    }

    @Override
    public void softDelete(Long id) {
        categoryJpaRepository.softDelete(id);
    }

    @Override
    public void updateName(Long id, String newDisplayName, String newNormalizedName) {
        categoryJpaRepository.updateName(id, newDisplayName, newNormalizedName);
    }

    @Override
    public boolean existsByNormalizedNameAndNotId(String normalizedName, Long id) {
        return categoryJpaRepository.existsByNormalizedNameAndNotId(normalizedName,id);
    }

    @Override
    public Optional<Category> findByIdAndActiveTrue(Long id) {
        return categoryJpaRepository
                .findByIdAndActiveTrue(id)
                .map(CategoryEntityMapper::toDomain);
    }


}
