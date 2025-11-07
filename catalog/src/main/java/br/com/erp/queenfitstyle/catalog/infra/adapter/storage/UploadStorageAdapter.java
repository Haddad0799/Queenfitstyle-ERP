package br.com.erp.queenfitstyle.catalog.infra.adapter.storage;

import br.com.erp.queenfitstyle.catalog.domain.port.out.UploadStoragePort;
import br.com.erp.queenfitstyle.upload.domain.port.UploadServicePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UploadStorageAdapter implements UploadStoragePort {

    private final UploadServicePort uploadService;

    public UploadStorageAdapter(UploadServicePort uploadService) {
        this.uploadService = uploadService;
    }

    @Override
    public List<String> generatePresignedUrls(String pathPrefix, List<String> filenames) {
        return uploadService.generatePresignedUrls(pathPrefix,filenames);
    }
}
