package br.com.erp.queenfitstyle.catalog.infra.adapter.repository;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.domain.port.out.ColorRepositoryPort;
import br.com.erp.queenfitstyle.catalog.infra.mapper.ColorEntityMapper;
import br.com.erp.queenfitstyle.catalog.infra.repository.ColorJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ColorRepositoryAdapter implements ColorRepositoryPort {

    private final ColorJpaRepository colorJpaRepository;

    public ColorRepositoryAdapter(ColorJpaRepository colorJpaRepository) {
        this.colorJpaRepository = colorJpaRepository;
    }

    @Override
    public Optional<Color> findByid(Long id) {
        return colorJpaRepository
                .findById(id)
                .map(ColorEntityMapper::toDomain);
    }

    @Override
    public Optional<Color> findByNormalizedNameAndActiveTrue(String name) {
        return colorJpaRepository
                .findByNormalizedNameAndActiveTrue(name)
                .map(ColorEntityMapper::toDomain);
    }
}
