package br.com.erp.queenfitstyle.catalog.domain.port.in;

import br.com.erp.queenfitstyle.catalog.application.command.ImportProductCommand;
import br.com.erp.queenfitstyle.catalog.domain.entity.Product;

import java.util.List;

public interface ImportProductsUseCase {

    List<Product> execute(List<ImportProductCommand> commands);

}
