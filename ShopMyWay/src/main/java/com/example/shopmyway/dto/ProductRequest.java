package com.example.shopmyway.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductRequest {
    private Long productId;
    private String productName;

    private String description;

    private Integer quantity;

    private BigDecimal price;
    private Boolean active = true;
    private LocalDateTime createAt;

    public ProductRequest(Long productId, String productName, String description, Integer quantity, BigDecimal price, Boolean active, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.active = active;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public ProductRequest() {
    }

    private  LocalDateTime updatedAt;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

