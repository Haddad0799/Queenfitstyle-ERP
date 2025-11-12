package br.com.erp.queenfitstyle.upload.domain.port;

import java.util.List;

public interface UploadServicePort {

    /**
     * Gera URLs pré-assinadas para upload de imagens.
     *
     * @param pathPrefix caminho base (ex: "products/{skuCode}")
     * @param filenames nomes dos arquivos que serão enviados
     * @return lista de URLs pré-assinadas
     */
    List<String> generatePresignedUrls(String pathPrefix, List<String> filenames);
}
