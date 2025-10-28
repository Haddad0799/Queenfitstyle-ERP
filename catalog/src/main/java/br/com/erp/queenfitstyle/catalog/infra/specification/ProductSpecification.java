package br.com.erp.queenfitstyle.catalog.infra.specification;

import br.com.erp.queenfitstyle.catalog.infra.entity.ProductEntity;
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

    public static Specification<ProductEntity> hasColor(Long colorId) {
        return (root, query, cb) -> {
            if (colorId == null) {
                return cb.conjunction();
            }
            // JOIN com SKUs
            var skusJoin = root.join("skus");
            return cb.equal(skusJoin.get("color").get("id"), colorId);
        };
    }


    // 🔹 Exemplo extra (você pode expandir depois)
    public static Specification<ProductEntity> priceGreaterThan(Double minPrice) {
        return (root, query, cb) -> minPrice == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }
}
