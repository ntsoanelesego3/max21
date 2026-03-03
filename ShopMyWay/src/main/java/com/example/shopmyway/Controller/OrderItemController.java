package com.example.shopmyway.Controller;

import com.example.shopmyway.Services.OrderItemService;
import com.example.shopmyway.dto.OrderItemRequest;
import com.example.shopmyway.models.OrderItem;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ShopMyWay")
public class OrderItemController {

    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @PostMapping(path = "/item", consumes = {"application/json"})
    public OrderItem addOrderItem(@RequestBody OrderItemRequest request) {
        return service.addOrderItem(request);
    }
}