package com.example.shopmyway.dao;

import com.example.shopmyway.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {

}
