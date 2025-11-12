package br.com.erp.queenfitstyle.catalog.domain.port.out;

import java.util.List;

public interface UploadStoragePort {
    List<String> generatePresignedUrls(String pathPrefix, List<String> filenames);
}
