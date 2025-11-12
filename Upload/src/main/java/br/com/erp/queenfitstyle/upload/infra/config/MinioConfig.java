package br.com.erp.queenfitstyle.upload.infra.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    private final String minioUrl;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;

    public MinioConfig(
            @Value("${minio.url}") String minioUrl,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey,
            @Value("${minio.bucket}") String bucketName) {
        this.minioUrl = minioUrl;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }

    @Bean
    public MinioClient minioClientInternal() throws Exception {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();

        // Cria o bucket se não existir
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Aplica política pública ao bucket
        String policyJson = """
            {
              "Version": "2012-10-17",
              "Statement": [
                {
                  "Effect": "Allow",
                  "Principal": "*",
                  "Action": [
                    "s3:GetObject"
                  ],
                  "Resource": [
                    "arn:aws:s3:::%s/*"
                  ]
                }
              ]
            }
            """.formatted(bucketName);

        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJson)
                .build());

        return minioClient;
    }
}
