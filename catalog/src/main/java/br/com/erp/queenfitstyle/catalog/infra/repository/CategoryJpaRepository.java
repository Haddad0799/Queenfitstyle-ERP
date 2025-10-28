package br.com.erp.queenfitstyle.catalog.infra.repository;

import br.com.erp.queenfitstyle.catalog.infra.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByNormalizedName(String normalizedName);
    Page<CategoryEntity> findAllByActive(Boolean active, Pageable pageable);

    @Modifying
    @Query("UPDATE CategoryEntity c SET c.active = false WHERE c.id = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Query("UPDATE CategoryEntity c SET c.displayName = :displayName, c.normalizedName = :normalizedName WHERE c.id = :id")
    void updateName(@Param("id") Long id,
                    @Param("displayName") String displayName,
                    @Param("normalizedName") String normalizedName);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM CategoryEntity c " +
            "WHERE c.normalizedName = :normalizedName AND c.id <> :id")
    boolean existsByNormalizedNameAndNotId(@Param("normalizedName") String normalizedName,
                                           @Param("id") Long id);

    Optional<CategoryEntity> findByIdAndActiveTrue(@Param("id") Long id);

}
