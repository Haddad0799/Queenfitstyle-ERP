package br.com.erp.queenfitstyle.catalog.infra.repository;

import br.com.erp.queenfitstyle.catalog.infra.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {
}
