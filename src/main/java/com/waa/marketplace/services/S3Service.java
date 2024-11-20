package com.waa.marketplace.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String saveFile(MultipartFile file, String name) throws IOException;

    void deleteFile(List<String> fileNames);
}
