package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Image;
import br.com.erp.queenfitstyle.catalog.infra.entity.ImageEntity;

import java.util.UUID;

public class ImageEntityMapper {

    public static ImageEntity toEntity(Image image) {
        if (image == null) return null;

        return new ImageEntity(
                image.getId().toString(),
                image.getFilename(),
                image.getPublicUrl(),
                image.getDisplayOrder()
        );
    }

    public static Image toDomain(ImageEntity entity) {
        if (entity == null) return null;

        return new Image(
                UUID.fromString(entity.getUuid()),
                entity.getFilename(),
                entity.getPublicUrl(),
                entity.getDisplayOrder()
        );
    }
}
