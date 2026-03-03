package com.example.shopmyway.Controller;

import com.example.shopmyway.Services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/api/v1")
public class ProductImageController {

    @Autowired
    private ProductImageService imageService;


    @GetMapping("/product/{id}/upload-form")
    public String showUploadForm(@PathVariable("id") Integer productId, Model model) {
        model.addAttribute("productId", productId); // passes productId into the HTML template
        return "upload"; // looks for html file and return it
    }


    @PostMapping("/product/{id}/image")
    public ResponseEntity<String> uploadImage(
            @PathVariable("id") Integer productId,
            @RequestParam("image") MultipartFile file
    ) {
        // Basic validation — don't let empty files through
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        // Only allow image files
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body("Only image files are allowed (jpg, png, gif, etc.)");
        }

        try {
            String result = imageService.uploadImage(productId, file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }


    // The browser receives raw bytes + content type = displays image
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer productId) {
        byte[] imageData = imageService.getImage(productId);
        String imageType = imageService.getImageType(productId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageType)) // tells browser "this is a jpeg/png/etc"
                .body(imageData);
    }
}