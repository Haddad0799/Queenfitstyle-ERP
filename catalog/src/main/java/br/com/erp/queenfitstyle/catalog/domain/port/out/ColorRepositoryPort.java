package br.com.erp.queenfitstyle.catalog.domain.port.out;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;

import java.util.Optional;

public interface ColorRepositoryPort {

    Optional<Color> findByid(Long id);
}
