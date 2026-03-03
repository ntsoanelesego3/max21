package com.example.shopmyway.Services;

import com.example.shopmyway.dao.ProductImageRepo;
import com.example.shopmyway.dao.ProductRepo;
import com.example.shopmyway.models.Product;
import com.example.shopmyway.models.ProductImage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepo imageRepo;

    @Autowired
    private ProductRepo productRepo;

    public String uploadImage(Integer productId, MultipartFile file) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        // ✅ Add this — confirms product was actually found
        System.out.println("Found product: " + product.getProductId() + " - " + product.getProductName());

        imageRepo.findByProductId(productId).ifPresent(imageRepo::delete);

        byte[] bytes = file.getBytes();

        ProductImage image = new ProductImage();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(bytes);
        image.setProduct(product); //set explicitly using setter

        ProductImage saved = imageRepo.save(image);
        System.out.println("Saved image ID: " + saved.getId());

        return "Image uploaded successfully for product: " + productId;
    }

    public byte[] getImage(Integer productId) {
        ProductImage image = imageRepo.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("No image found for product: " + productId));

        System.out.println("Retrieved image size: " + image.getImageData().length);
        return image.getImageData(); // return raw bytes directly
    }

    public String getImageType(Integer productId) {
        ProductImage image = imageRepo.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("No image found for product: " + productId));
        return image.getType();
    }
}