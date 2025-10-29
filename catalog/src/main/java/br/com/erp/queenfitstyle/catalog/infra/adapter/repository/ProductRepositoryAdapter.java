package br.com.erp.queenfitstyle.catalog.infra.adapter.repository;

import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ProductRepositoryPort;
import br.com.erp.queenfitstyle.catalog.infra.entity.ProductEntity;
import br.com.erp.queenfitstyle.catalog.infra.mapper.ProductEntityMapper;
import br.com.erp.queenfitstyle.catalog.infra.repository.ProductJpaRepository;
import br.com.erp.queenfitstyle.catalog.infra.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity;
        if (product.getId() == null) {
            entity = ProductEntityMapper.toNewEntity(product, true);
        } else {
            entity = ProductEntityMapper.toExistingEntity(product, true);
        }

        entity = productJpaRepository.save(entity);
        return ProductEntityMapper.toDomain(entity, true);

    }


    @Override
    public boolean existsByNameAndCategoryId(String normalizedName, Long categoryId) {
        return productJpaRepository.existsByNameAndCategoryId(normalizedName,categoryId);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository
                .findById(id)
                .map(ProductEntityMapper::toDomain);
    }

    @Override
    public Page<Product> findAllFiltered(Long categoryId, Boolean active, String name, Long colorId, String sizeFilter, Pageable pageable) {

        Specification<ProductEntity> spec = ProductSpecification.hasCategory(categoryId)
                .and(ProductSpecification.isActive(active))
                .and(ProductSpecification.nameContains(name))
                .and(ProductSpecification.hasColorAndSize(colorId, sizeFilter));

        Page<ProductEntity> entities = productJpaRepository.findAll(spec, pageable);

        return entities.map(ProductEntityMapper::toDomain);
    }

    @Override
    public Optional<Product> findBySkuCode(String skuCode) {
        return productJpaRepository
                .findBySkuCode(skuCode)
                .map(ProductEntityMapper::toDomain);
    }

    @Override
    public Optional<Product> findProductWithSku(Long productId, String skuCode) {

        return productJpaRepository
                .findProductWithSku(productId,skuCode)
                .map(ProductEntityMapper::toDomain);
    }

    @Override
    public List<Product> findBySlugInAndActiveTrue(Set<String> slugs) {
        return productJpaRepository
                .findBySlugInAndActiveTrue(slugs)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .toList();
    }

}
