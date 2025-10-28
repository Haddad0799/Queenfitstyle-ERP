package br.com.erp.queenfitstyle.catalog.infra.repository;

import br.com.erp.queenfitstyle.catalog.infra.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long>,
        JpaSpecificationExecutor<ProductEntity> {

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM ProductEntity p
    WHERE p.name = :name
      AND p.category.id = :categoryId
""")
    boolean existsByNameAndCategoryId(
            @Param("name") String name,
            @Param("categoryId") Long categoryId
    );

    @Query("""
    SELECT DISTINCT p FROM ProductEntity p
    JOIN FETCH p.skus s
    WHERE s.skuCode = :skuCode
""")
    Optional<ProductEntity> findBySkuCode(@Param("skuCode") String skuCode);

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.skus s WHERE p.id = :productId AND s.skuCode = :skuCode")
    Optional<ProductEntity> findProductWithSku(@Param("productId") Long productId, @Param("skuCode") String skuCode);




}
