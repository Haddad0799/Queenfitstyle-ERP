package br.com.erp.queenfitstyle.catalog.domain.port.out;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ColorRepositoryPort {

    Optional<Color> findByid(Long id);

    Optional<Color> findByNormalizedNameAndActiveTrue(String name);

    List<Color> findByNormalizedNameInAndActiveTrue(Set<String> colorNames);
}
