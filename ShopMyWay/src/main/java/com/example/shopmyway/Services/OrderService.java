package com.example.shopmyway.Services;

import com.example.shopmyway.dao.OrdersRepo;
import com.example.shopmyway.dao.UserRepo;
import com.example.shopmyway.dto.OrderRequest;
import com.example.shopmyway.models.Orders;
import com.example.shopmyway.models.Users;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// ✅ @Service = this class is the "chef" — it handles business logic only
@Service
public class OrderService {

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private UserRepo usersRepo;

    public Orders addOrder(OrderRequest request) {
        // Find the user, or throw a clear error if they don't exist
        Users user = usersRepo.findById(Math.toIntExact(request.getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + request.getUserId()));

        // Build the new order
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setStatus(request.getStatus());
        orders.setTotalAmount(request.getTotalAmount());
        orders.setShippingAddress(request.getShippingAddress());
        orders.setCreatedAt(LocalDateTime.now());
        orders.setUpdatedAt(LocalDateTime.now());

        return repo.save(orders);
    }

    public List<Orders> getOrders() {
        return repo.findAll();
    }
    public Optional<Orders> getOrder(Long order_id) {
        return repo.findById(order_id);
    }
}