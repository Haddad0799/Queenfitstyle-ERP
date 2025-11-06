package br.com.erp.queenfitstyle.catalog.web.factory;

import br.com.erp.queenfitstyle.catalog.web.dto.product.request.CreateProductDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.request.ImportProductDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.request.ImportProductsDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.product.request.UpdateProductDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.CreateProductSkuDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.ImportProductSkuDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.sku.request.UpdateSkuDto;
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

    public static CreateSkuCommand createSkuFromDTO(CreateProductSkuDTO dto) {
        return new CreateSkuCommand(
                dto.colorId(),
                dto.size(),
                dto.price(),
                dto.inventory()
        );
    }

    public static CreateSkuToProductCommand createProductSkuDTO(Long productId,CreateProductSkuDTO dto) {
        return new CreateSkuToProductCommand(
                productId,
                dto.colorId(),
                dto.size(),
                dto.price(),
                dto.inventory()
        );
    }

}
