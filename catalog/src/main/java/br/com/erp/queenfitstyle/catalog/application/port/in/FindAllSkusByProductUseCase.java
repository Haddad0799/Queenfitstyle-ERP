package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Sku;

import java.util.List;

public interface FindAllSkusByProductUseCase {

    List<Sku> execute(Long id);
}
