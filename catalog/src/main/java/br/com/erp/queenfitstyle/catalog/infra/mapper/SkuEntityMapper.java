package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Image;
import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Inventory;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Size;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;
import br.com.erp.queenfitstyle.catalog.infra.entity.ImageEntity;
import br.com.erp.queenfitstyle.catalog.infra.entity.SkuEntity;

import java.util.List;
import java.util.stream.Collectors;

public class SkuEntityMapper {

    private SkuEntityMapper() {}

    // ---------- Entity -> Domain ----------
    public static Sku toDomain(SkuEntity entity) {
        if (entity == null) return null;

        List<Image> images = null;
        if (entity.getImages() != null && !entity.getImages().isEmpty()) {
            images = entity.getImages()
                    .stream()
                    .map(ImageEntityMapper::toDomain)
                    .collect(Collectors.toList());
        }

        return new Sku(
                entity.getId(),
                new SkuCode(entity.getSkuCode()),
                ColorEntityMapper.toDomain(entity.getColor()),
                new Size(entity.getSize()),
                new Price(entity.getPrice()),
                entity.isActive(),
                new Inventory(entity.getInventory()),
                images
        );
    }

    // ---------- Domain -> New Entity ----------
    public static SkuEntity toNewEntity(Sku sku) {
        if (sku == null) return null;

        SkuEntity skuEntity = new SkuEntity(
                sku.getCode(),
                ColorEntityMapper.toExistingEntity(sku.getColor()),
                sku.getSize(),
                sku.getPrice(),
                sku.getInventory()
        );

        if (sku.getImages() != null && !sku.getImages().isEmpty()) {
            List<ImageEntity> imageEntities = sku.getImages()
                    .stream()
                    .map(ImageEntityMapper::toEntity)
                    .toList();

            imageEntities.forEach(skuEntity::addImage);

        }

        return skuEntity;
    }

    // ---------- Domain -> Existing Entity ----------
    public static SkuEntity toExistingEntity(Sku sku) {
        if (sku == null) return null;

        SkuEntity skuEntity = new SkuEntity(
                sku.getId(),
                sku.getCode(),
                ColorEntityMapper.toExistingEntity(sku.getColor()),
                sku.getSize(),
                sku.getPrice(),
                sku.getInventory(),
                sku.isActive()
        );

        if (sku.getImages() != null && !sku.getImages().isEmpty()) {
            List<ImageEntity> imageEntities = sku.getImages()
                    .stream()
                    .map(ImageEntityMapper::toEntity)
                    .toList();

            imageEntities.forEach(skuEntity::addImage);
        }

        return skuEntity;
    }

}
