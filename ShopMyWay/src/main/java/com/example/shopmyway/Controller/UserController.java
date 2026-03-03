package com.example.shopmyway.Controller;

import com.example.shopmyway.Services.UserService;
import com.example.shopmyway.dto.RegisterUser;
import com.example.shopmyway.models.Users;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ShopMyWay")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

//    @PostMapping(path = "/user", consumes = {"application/json"})
//    public Users UserGet(@RequestBody Users user){
//        return service.addUser(user);
//    }

    @GetMapping(path = "/users", produces = {"application/json"})
    public List<Users> UserGet(){
        return service.getUser();
    }

    @PostMapping(path = "/register", consumes = {"application/json"})
    public Users registerUser(@RequestBody RegisterUser registerUser){
        return service.register(registerUser);

    }
}
