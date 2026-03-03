package com.example.shopmyway.dao;

import com.example.shopmyway.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);
//    Users findById(Users user);
}
