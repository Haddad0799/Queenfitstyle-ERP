package br.com.erp.queenfitstyle.upload.infra.adapter;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MinioUploadAdapter {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioUploadAdapter(MinioClient minioClient, @Value("${minio.bucket}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    public List<String> generatePresignUrls(String pathPrefix, List<String> filenames) {
        return filenames.stream()
                .map(filename -> {
                    try {
                        String objectName = pathPrefix + "/" + filename;
                        return minioClient.getPresignedObjectUrl(
                                GetPresignedObjectUrlArgs.builder()
                                        .method(Method.PUT)
                                        .bucket(bucketName)
                                        .object(objectName)
                                        .expiry(60 * 10)
                                        .build()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao gerar URL presignada para " + filename, e);
                    }
                })
                .collect(Collectors.toList());
    }
}
