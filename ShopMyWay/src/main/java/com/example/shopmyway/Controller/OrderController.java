package com.example.shopmyway.Controller;

import com.example.shopmyway.Services.OrderService;
import com.example.shopmyway.dto.OrderRequest;
import com.example.shopmyway.models.Orders;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ShopMyWay")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }


    @PostMapping(path = "/order", consumes = {"application/json"})
    public Orders OrderGet(@RequestBody OrderRequest request){
        return service.addOrder(request);
    }

    // get all the orders remove later
    @GetMapping(path = "orders", produces = {"application/json"})
    public List<Orders> OrdersGet(){
        return service.getOrders();
    }

    @GetMapping(path = "/order/{id}", produces = {"application/json"})
    public Optional<Orders> GetOrder(@PathVariable("id") Long order_id){
        return service.getOrder(order_id);

    }
}
