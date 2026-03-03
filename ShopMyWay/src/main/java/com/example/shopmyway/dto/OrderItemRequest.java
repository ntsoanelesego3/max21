package com.example.shopmyway.dto;

// DTO = Data Transfer Object
// Think of it like a FORM someone fills in when placing an order item.
// It only contains exactly what the user sends us — nothing extra.
public class OrderItemRequest {

    // Which order does this item belong to?
    private Long orderId;
    // Which product are they ordering?
    private Integer productId;
    // How many do they want?
    private Integer quantity;

    public OrderItemRequest() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}