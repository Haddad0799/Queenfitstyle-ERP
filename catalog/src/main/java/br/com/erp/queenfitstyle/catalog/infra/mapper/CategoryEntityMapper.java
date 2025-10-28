package br.com.erp.queenfitstyle.catalog.infra.mapper;

import br.com.erp.queenfitstyle.catalog.domain.entity.Category;
import br.com.erp.queenfitstyle.catalog.infra.entity.CategoryEntity;

public class CategoryEntityMapper {

   public static CategoryEntity toNewEntity(Category category) {
       return new CategoryEntity(category.getDisplayName(),
               category.getNormalizedName(),
               category.isActive());
   }

    public static CategoryEntity toExistentEntity(Category category) {
        return new CategoryEntity(category.getId(), category.getDisplayName(),
                category.getNormalizedName(),
                category.isActive());
    }

   public static Category toDomain(CategoryEntity categoryEntity) {
       return new Category(categoryEntity.getId(),
               categoryEntity.getDisplayName(),
               categoryEntity.getActive());
   }
}
