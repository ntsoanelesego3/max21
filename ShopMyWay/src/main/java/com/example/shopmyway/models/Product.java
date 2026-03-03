package com.example.shopmyway.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String productName;

    @Column(length = 2000)
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    private Boolean active = true;
    private LocalDateTime createAt;

    // ✅ Fixed typo: "udpated_at" → "updated_at"
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(Integer productId, String productName, String description, Integer quantity,
                   BigDecimal price, Boolean active, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.active = active;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreateAt() { return createAt; }
    public void setCreateAt(LocalDateTime createAt) { this.createAt = createAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}