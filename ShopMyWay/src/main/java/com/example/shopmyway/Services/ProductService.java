package com.example.shopmyway.Services;

import com.example.shopmyway.dao.ProductRepo;
import com.example.shopmyway.models.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// ✅ Chef only — no waiter annotations here
@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public Product addProducts(Product product) {
        product.setCreateAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setActive(Boolean.TRUE);
        return repo.save(product);
    }

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Optional<Product> getProduct(int productId) {
        return repo.findById(productId);
    }

    public String deleteProduct(int productId) {
        repo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with ID: " + productId));
        repo.deleteById(productId);
        return "Product " + productId + " deleted successfully";
    }

    public Product updateProducts(int productId, Product updateProduct) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with ID: " + productId));

        product.setProductName(updateProduct.getProductName());
        product.setDescription(updateProduct.getDescription());
        product.setQuantity(updateProduct.getQuantity());
        product.setPrice(updateProduct.getPrice());
        product.setUpdatedAt(LocalDateTime.now());

        return repo.save(product);
    }
}