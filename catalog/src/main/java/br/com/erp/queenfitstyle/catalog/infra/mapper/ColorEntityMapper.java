package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.ColorName;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Hexadecimal;
import br.com.erp.queenfitstyle.catalog.infra.entity.ColorEntity;

public class ColorEntityMapper {


    public static ColorEntity toNewEntity(Color color) {
        if (color == null) return null;
        return new ColorEntity(color.getDisplayName(),
                color.getNormalizedName(),
                color.getHexCode(),
                color.isActive()
                );
    }


    public static ColorEntity toExistingEntity(Color color) {
        if (color == null) return null;
        return new ColorEntity(color.getId(),
                color.getDisplayName(),
                color.getNormalizedName(),
                color.getHexCode(),
                color.isActive());
    }

    public static Color toDomain(ColorEntity entity) {
        if (entity == null) return null;


        return new Color(entity.getId(),
                new ColorName(entity.getName(), entity.getNormalizedName()),
                new Hexadecimal(entity.getHexaCode()),
                entity.isActive());
    }
}
