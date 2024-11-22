package com.waa.marketplace.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void uploadImage(MultipartFile file, Long productId) throws IOException;
}
