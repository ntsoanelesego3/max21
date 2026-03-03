package com.example.shopmyway.dto;

public class OrderRequest {
    private Long userId;
    private String status;
    private Double totalAmount;
    private String shippingAddress;


    public OrderRequest(Long userId, String status, Double totalAmount, String shippingAddress) {
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}