package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Inventory;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Price;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.Size;
import br.com.erp.queenfitstyle.catalog.domain.valueobject.SkuCode;
import br.com.erp.queenfitstyle.catalog.infra.entity.SkuEntity;

public class SkuEntityMapper {

    private SkuEntityMapper() {}


    public static Sku toDomain(SkuEntity entity) {
        if (entity == null) return null;

        return new Sku(
                entity.getId(),
                new SkuCode(entity.getSkuCode()),
                ColorEntityMapper.toDomain(entity.getColor()),
                new Size(entity.getSize()),
                new Price(entity.getPrice()),
                entity.isActive(),
                new Inventory(entity.getInventory())
        );
    }

    // Domínio -> Entidade
    public static SkuEntity toNewEntity(Sku sku) {
        if (sku == null) return null;

        return new SkuEntity(sku.getCode(),
                ColorEntityMapper.toExistingEntity(sku.getColor()),
                sku.getSize(),
                sku.getPrice(),
                sku.getInventory());
    }

    public static SkuEntity toExistingEntity(Sku sku) {
        if (sku == null) return null;

        return new SkuEntity(
                sku.getId(),
                sku.getCode(),
                ColorEntityMapper.toExistingEntity(sku.getColor()),
                sku.getSize(),
                sku.getPrice(),
                sku.getInventory(),
                sku.isActive()
        );

    }
}
