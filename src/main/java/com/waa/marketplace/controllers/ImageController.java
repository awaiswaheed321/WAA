package com.waa.marketplace.controllers;

import com.waa.marketplace.services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    public final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{productId}")
    public ResponseEntity<?> uploadImage(
            @RequestBody MultipartFile file,
            @PathVariable Long productId) throws IOException {
        imageService.uploadImage(file, productId);
        return ResponseEntity.ok().build();
    }
}
