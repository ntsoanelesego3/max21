package com.example.restapi;

import com.example.restapi.api.SoftwareEngineer;
import com.example.restapi.dao.SoftwareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoftwareEngineerController {

    @Autowired
    SoftwareRepo repo;

    @RequestMapping("/")
    public String home(){
        return "hello there!";
    }

//    public String addEngineer() {
//        return
//    }
}