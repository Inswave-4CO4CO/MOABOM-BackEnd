package com.moabom.backend.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = "profile/" + UUID.randomUUID() + "_" + originalFileName;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }

        // 퍼블릭 URL로 접근 가능 (기본 설정 기준)
        return "https://" + bucket + ".s3.amazonaws.com/" + fileName;
    }
}
