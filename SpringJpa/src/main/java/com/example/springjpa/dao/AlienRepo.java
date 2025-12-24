package com.example.springjpa.dao;

import com.example.springjpa.model.Alien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface AlienRepo extends JpaRepository<Alien, Integer> {
//    List<Alien> findByTech(String Tech);
//    List<Alien> findByAidGreaterThan(int aid);
//
//    @Query("FROM Alien WHERE tech=?1 ORDER BY aname")
//    List<Alien> findByTechStored(String Tech);

}
