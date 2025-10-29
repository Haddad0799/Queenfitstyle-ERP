package br.com.erp.queenfitstyle.catalog.infra.repository;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.infra.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {
    Optional<ColorEntity> findByNormalizedNameAndActiveTrue(String name);
}
