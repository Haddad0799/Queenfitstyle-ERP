package br.com.erp.queenfitstyle.catalog.application.port.out;

import java.util.List;

public interface UploadStoragePort {
    List<String> generatePresignedUrls(String pathPrefix, List<String> filenames);
}
