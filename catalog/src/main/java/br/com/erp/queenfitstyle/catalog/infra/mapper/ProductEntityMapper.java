package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Product;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.ProductCode;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Slug;
import br.com.erp.queenfitstyle.catalog.infra.entity.ProductEntity;
import br.com.erp.queenfitstyle.catalog.infra.entity.SkuEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductEntityMapper {

    /** Converte ProductEntity (infra) → Product (domínio) */
    public static Product toDomain(ProductEntity entity, boolean includeSkus) {
        if (entity == null) return null;

        Product product = new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                CategoryEntityMapper.toDomain(entity.getCategory()),
                new Price(entity.getBasePrice()),
                new ProductCode(entity.getProductCode()),
                new Slug(entity.getSlug()),
                entity.isActive()
        );

        if (includeSkus && entity.getSkus() != null && !entity.getSkus().isEmpty()) {
            product.addSkus(
                    entity.getSkus().stream()
                            .map(SkuEntityMapper::toDomain)
                            .collect(Collectors.toSet())
            );
        }

        return product;
    }

    /** Converte Product (domínio) → ProductEntity para novo produto (insert) */
    public static ProductEntity toNewEntity(Product product, boolean includeSkus) {
        if (product == null) return null;

        ProductEntity entity = new ProductEntity(
                product.getName(),
                product.getDescription(),
                CategoryEntityMapper.toExistentEntity(product.getCategory()),
                product.getPrice(),
                product.getCode(),
                product.getSlug(),
                product.isActive()
        );

        if (includeSkus && product.getSkus() != null && !product.getSkus().isEmpty()) {
            Set<SkuEntity> skuEntities = product.getSkus().stream()
                    .map(SkuEntityMapper::toNewEntity)
                    .collect(Collectors.toSet());
            skuEntities.forEach(sku -> sku.setProduct(entity));
            entity.setSkus(skuEntities);
        }

        return entity;
    }

    /** Converte Product (domínio) → ProductEntity para produto existente (update) */
    public static ProductEntity toExistingEntity(Product product, boolean includeSkus) {
        if (product == null) return null;

        ProductEntity entity = new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                CategoryEntityMapper.toExistentEntity(product.getCategory()),
                product.getPrice(),
                product.getCode(),
                product.getSlug(),
                product.isActive()
        );

        if (includeSkus && product.getSkus() != null && !product.getSkus().isEmpty()) {
            Set<SkuEntity> skuEntities = product.getSkus().stream()
                    .map(SkuEntityMapper::toExistingEntity)
                    .collect(Collectors.toSet());
            skuEntities.forEach(sku -> sku.setProduct(entity));
            entity.setSkus(skuEntities);
        }

        return entity;
    }

    /** Versões sem SKUs para conveniência */
    public static Product toDomain(ProductEntity entity) {
        return toDomain(entity, true);
    }

    public static ProductEntity toNewEntity(Product product) {
        return toNewEntity(product, false);
    }

    public static ProductEntity toExistingEntity(Product product) {
        return toExistingEntity(product, false);
    }
}

