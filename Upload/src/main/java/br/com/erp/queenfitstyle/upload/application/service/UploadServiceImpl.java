package br.com.erp.queenfitstyle.upload.application.service;

import br.com.erp.queenfitstyle.upload.domain.port.UploadServicePort;
import br.com.erp.queenfitstyle.upload.infra.adapter.MinioUploadAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadServiceImpl implements UploadServicePort {

    private final MinioUploadAdapter minioUploadAdapter;

    public UploadServiceImpl(MinioUploadAdapter minioUploadAdapter) {
        this.minioUploadAdapter = minioUploadAdapter;
    }

    @Override
    public List<String> generatePresignedUrls(String pathPrefix, List<String> filenames) {
        return minioUploadAdapter.generatePresignUrls(pathPrefix, filenames);
    }
}
