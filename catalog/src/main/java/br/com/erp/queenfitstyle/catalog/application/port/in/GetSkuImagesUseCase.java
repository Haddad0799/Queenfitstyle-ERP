package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Image;

import java.util.List;

public interface GetSkuImagesUseCase {
    List<Image> execute(Long productId, String skuCode);
}
