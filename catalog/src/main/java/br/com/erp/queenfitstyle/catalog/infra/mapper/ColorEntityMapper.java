package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.infra.entity.ColorEntity;

public class ColorEntityMapper {


    public static ColorEntity toNewEntity(Color color) {
        if (color == null) return null;
        return new ColorEntity(color.getName(),
                color.getNormalizedName(),
                color.getHexCode(),
                color.isActive()
                );
    }


    public static ColorEntity toExistingEntity(Color color) {
        if (color == null) return null;
        return new ColorEntity(color.getId(),
                color.getName(),
                color.getNormalizedName(),
                color.getHexCode(),
                color.isActive());
    }

    public static Color toDomain(ColorEntity entity) {
        if (entity == null) return null;
        return new Color(entity.getId(), entity.getName(),
                entity.getNormalizedName(),
                entity.getHexaCode(),
                entity.isActive());
    }
}
