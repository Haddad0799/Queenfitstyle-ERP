package br.com.erp.queenfitstyle.catalog.infra.repository;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.infra.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {
    Optional<ColorEntity> findByNormalizedNameAndActiveTrue(String name);

    List<ColorEntity> findByNormalizedNameInAndActiveTrue(Set<String> colorNames);
}
