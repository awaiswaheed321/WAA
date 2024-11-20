package com.waa.marketplace.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    final S3Properties s3Properties;

    public S3Config(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder().region(Region.of(s3Properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(s3Properties.getAccessKeyId(),
                                s3Properties.getSecretAccessKey()))).build();
    }
}
