package com.example.restapi.dao;

import com.example.restapi.api.SoftwareEngineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareRepo extends JpaRepository<SoftwareEngineer, Integer> {
}
