package br.com.erp.queenfitstyle.catalog.api.factory;

import br.com.erp.queenfitstyle.catalog.api.dto.product.request.CreateProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.ImportProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.ImportProductsDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.UpdateProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.sku.request.ImportProductSkuDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.sku.request.UpdateSkuDto;
import br.com.erp.queenfitstyle.catalog.application.command.*;

import java.util.List;

public class ProductCommandFactory {

    private ProductCommandFactory() {} // impede instâncias

    public static CreateProductCommand createFromDTO(CreateProductDTO dto) {
        List<CreateSkuCommand> skuCommands = dto.skus() == null ? List.of() :
                dto.skus().stream()
                        .map(skuDto -> new CreateSkuCommand(
                                skuDto.colorId(),
                                skuDto.size(),
                                skuDto.price(),
                                skuDto.inventory()
                        ))
                        .toList();

        return new CreateProductCommand(
                dto.name(),
                dto.description(),
                dto.categoryId(),
                dto.basePrice(),
                skuCommands
        );
    }

    public static UpdateProductCommand updateFromDTO(Long id, UpdateProductDTO dto) {
        return new UpdateProductCommand(
                id,
                dto.name(),
                dto.description(),
                dto.categoryId(),
                dto.basePrice(),
                dto.active()
        );
    }

    public static UpdateSkuCommand UpdateProductSkuFromDTO(Long id, String skuCode, UpdateSkuDto dto) {
        return new UpdateSkuCommand(id,
                skuCode,
                dto.price(),
                dto.inventory());
    }

    public static List<ImportProductCommand> fromImportDTO(ImportProductsDTO dto) {
        return dto.products().stream()
                .map(ProductCommandFactory::toCommand)
                .toList();
    }

    private static ImportProductCommand toCommand(ImportProductDTO productDTO) {
        List<ImportSkuCommand> skuCommands = productDTO.skus().stream()
                .map(ProductCommandFactory::toSkuCommand)
                .toList();

        return new ImportProductCommand(
                productDTO.name(),
                productDTO.description(),
                productDTO.categoryName(),
                productDTO.basePrice(),
                skuCommands
        );
    }

    private static ImportSkuCommand toSkuCommand(ImportProductSkuDTO skuDTO) {
        return new ImportSkuCommand(
                skuDTO.colorName(),
                skuDTO.size(),
                skuDTO.inventory(),
                skuDTO.price()
        );
    }
}
