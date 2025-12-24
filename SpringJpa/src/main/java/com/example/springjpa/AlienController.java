package com.example.springjpa;

import com.example.springjpa.dao.AlienRepo;
import com.example.springjpa.model.Alien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class AlienController {

    @Autowired
    AlienRepo Repo;

    @RequestMapping("/")
    public String home(){
        return "home";

    }
    @PostMapping(path ="/alien", consumes = "application/json")
    public Alien addAlien(Alien alien){
        Repo.save(alien);
        return alien;
    }

    // get data by aid
//    @GetMapping("/getAlien")
//    public ModelAndView getAlien(@RequestParam  int aid){
//        ModelAndView mv = new ModelAndView();
//        Alien alien = Repo.findById(aid).orElse(new Alien());
//        System.out.println(Repo.findByTech("GO"));
//        System.out.println(Repo.findByAidGreaterThan(102));
//        System.out.println(Repo.findByTechStored("GO"));
//        mv.setViewName("showAlien");
//        mv.addObject(alien);
//        return mv;
//    }
    @RequestMapping(path = "/aliens", produces = "application/json")
    @ResponseBody()
    public List<Alien> getAliens(){

       return Repo.findAll();
    }

    @GetMapping("/alien/{aid}")
    public Optional<Alien> getAlien(@PathVariable("aid") int aid){
        System.out.println(Repo.findById(aid));
        return Repo.findById(aid);

    }

    @GetMapping("/removeAlienAid")
    public String removeAlienAid(@RequestParam int aid){
        Repo.deleteById(aid);
        return "home";
    }

//    @GetMapping("/updateAlien")
//    public String updateAlien(@RequestParam int aid){
//        Repo.deleteById(aid);
//
//
//    }
}
