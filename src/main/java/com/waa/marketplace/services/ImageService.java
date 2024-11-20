package com.waa.marketplace.services;

import com.waa.marketplace.dtos.responses.ImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageResponseDto uploadImage(MultipartFile file, Long productId) throws IOException;
}
