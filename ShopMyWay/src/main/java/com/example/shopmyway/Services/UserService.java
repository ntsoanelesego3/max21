package com.example.shopmyway.Services;

import com.example.shopmyway.dao.UserRepo;
import com.example.shopmyway.dto.RegisterUser;
import com.example.shopmyway.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;



//    public Users addUser(Users user) {
//        return repo.save(user);
//    }

    public List<Users> getUser() {
        return repo.findAll();
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public Users register(RegisterUser registerUser) {
        Users users = new Users();

        users.setCreatedAt(LocalDateTime.now());
        users.setPassword(encoder.encode(registerUser.getPassword()));
        users.setUsername(registerUser.getUsername());
        users.setRole("CUSTOMER");
        return repo.save(users);
    }
}