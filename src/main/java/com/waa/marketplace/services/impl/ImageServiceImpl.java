package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.responses.ImageResponseDto;
import com.waa.marketplace.entites.Image;
import com.waa.marketplace.entites.Product;
import com.waa.marketplace.repositories.ImageRepository;
import com.waa.marketplace.repositories.ProductRepository;
import com.waa.marketplace.services.ImageService;
import com.waa.marketplace.services.S3Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final S3Service s3Service;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ImageServiceImpl(S3Service s3Service, ImageRepository imageRepository, ProductRepository productRepository) {
        this.s3Service = s3Service;
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ImageResponseDto uploadImage(MultipartFile file, Long productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(
                "Product not found"));
        if (file.isEmpty()) {
            throw new IllegalStateException("File is empty");
        }
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String imageUrl = s3Service.saveFile(file, fileName);

        Image image = new Image();
        image.setName(fileName);
        image.setImageUrl(imageUrl);
        image.setContentType(file.getContentType());
        image.setProduct(product);

        Image savedImage = imageRepository.save(image);

        ImageResponseDto responseDto = new ImageResponseDto();
        responseDto.setId(savedImage.getId());
        responseDto.setName(savedImage.getName());
        responseDto.setImageUrl(savedImage.getImageUrl());
        responseDto.setContentType(savedImage.getContentType());
        return responseDto;
    }

    public static String generateUniqueFileName(String filename) {
        if (filename == null || filename.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        int dotIndex = filename.lastIndexOf(".");
        String name = (dotIndex != -1) ? filename.substring(0, dotIndex) : filename;
        String extension = (dotIndex != -1) ? filename.substring(dotIndex) : "";
        String baseName = name.length() > 10 ? name.substring(0, 10) : name;
        String uuid = UUID.randomUUID().toString();
        return baseName + "_" + uuid + extension;
    }
}
