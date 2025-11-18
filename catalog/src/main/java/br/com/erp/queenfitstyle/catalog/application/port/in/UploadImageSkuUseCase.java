package br.com.erp.queenfitstyle.catalog.application.port.in;

import java.util.List;

public interface UploadImageSkuUseCase {

    List<String> execute(Long productId, String skuCode, List<String> filenames);
}
