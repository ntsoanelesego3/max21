package com.example.shopmyway.Services;

import com.example.shopmyway.dao.OrderItemRepo;
import com.example.shopmyway.dao.OrdersRepo;
import com.example.shopmyway.dao.ProductRepo;
import com.example.shopmyway.dto.OrderItemRequest;
import com.example.shopmyway.models.OrderItem;
import com.example.shopmyway.models.Orders;
import com.example.shopmyway.models.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepo repo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrdersRepo ordersRepo;

    // ✅ Now takes a proper DTO (OrderItemRequest) instead of 3 separate weird params
    // A DTO is like a "delivery box" that holds all the info together neatly
    public OrderItem addOrderItem(OrderItemRequest request) {
        Orders order = ordersRepo.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + request.getOrderId()));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + request.getProductId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPrice(product.getPrice()); // ✅ Price comes from the product, not the user
        return repo.save(orderItem);
    }
}