package com.waa.marketplace.services.impl;

import com.waa.marketplace.services.S3Service;
import com.waa.marketplace.utils.S3Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public S3ServiceImpl(S3Client s3Client, S3Properties s3Properties) {
        this.s3Client = s3Client;
        this.s3Properties = s3Properties;
    }

    @Override
    public String saveFile(MultipartFile file, String name) throws IOException {
        byte[] bytes = file.getBytes();  //Multipart file uploaded on server
        InputStream inputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(name)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, bytes.length));
        return generateS3FileUrl(name);
    }

    private String generateS3FileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", s3Properties.getBucketName(), s3Properties.getRegion(), fileName);
    }

    @Override
    public void deleteFile(List<String> fileNames) {
        fileNames.forEach(fileName -> {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(fileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        });
    }
}
