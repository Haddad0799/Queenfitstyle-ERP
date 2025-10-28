package br.com.erp.queenfitstyle.catalog.api.factory;

import br.com.erp.queenfitstyle.catalog.api.dto.product.request.CreateProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.product.request.UpdateProductDTO;
import br.com.erp.queenfitstyle.catalog.api.dto.sku.request.UpdateSkuDto;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.CreateProductCommand;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.CreateSkuCommand;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.UpdateProductCommand;
import br.com.erp.queenfitstyle.catalog.application.usecase.product.command.UpdateSkuCommand;

import java.util.List;

public class ProductCommandFactory {

    private ProductCommandFactory() {} // impede instâncias

    public static CreateProductCommand createFromDTO(CreateProductDTO dto) {
        List<CreateSkuCommand> skuCommands = dto.skus() == null ? List.of() :
                dto.skus().stream()
                        .map(skuDto -> new CreateSkuCommand(
                                skuDto.color(),
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
}
