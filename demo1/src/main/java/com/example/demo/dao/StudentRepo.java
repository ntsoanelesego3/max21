package com.example.demo.dao;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import tools.jackson.databind.node.StringNode;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {
   // List<Student> removeStudentByStudent_number(String student_number);
    // find student by id
//    List<Student> findStudentById (int id);


}
