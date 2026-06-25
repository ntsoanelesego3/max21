package com.cisco.studnetservice.dao;

import com.cisco.studnetservice.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    Student findByEmail(String email);

}


