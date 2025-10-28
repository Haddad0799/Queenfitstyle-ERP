package br.com.erp.queenfitstyle.catalog.infra.specification;

import br.com.erp.queenfitstyle.catalog.infra.entity.ProductEntity;
import br.com.erp.queenfitstyle.catalog.infra.entity.SkuEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<ProductEntity> hasCategory(Long categoryId) {
        return (root, query, cb) -> categoryId == null
                ? cb.conjunction()
                : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<ProductEntity> isActive(Boolean active) {
        return (root, query, cb) -> active == null
                ? cb.conjunction()
                : cb.equal(root.get("active"), active);
    }

    public static Specification<ProductEntity> nameContains(String name) {
        return (root, query, cb) -> (name == null || name.isBlank())
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<ProductEntity> hasColorAndSize(Long colorId, String size) {
        return (root, query, cb) -> {
            if (colorId == null && (size == null || size.isBlank())) {
                return cb.conjunction();
            }

            Join<ProductEntity, SkuEntity> skus = root.join("skus", JoinType.INNER);

            var predicate = cb.conjunction();

            if (colorId != null) {
                predicate = cb.and(predicate, cb.equal(skus.get("color").get("id"), colorId));
            }

            if (size != null && !size.isBlank()) {
                predicate = cb.and(predicate, cb.equal(skus.get("size"), size.toUpperCase())); // uppercase
            }

            assert query != null;
            query.distinct(true);

            return predicate;
        };
    }



}
