package com.example.demo;

import com.example.demo.dao.StudentRepo;
import com.example.demo.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class StudentService {
    public List<Student> getStudent (){
        return List.of(
                new Student(1,"Lesego","Ntsoane","2344556"),
                new Student(2,"John","Smith","244444")

        );
    }

    // intize the interface
    private StudentRepo repo;

    public StudentService(StudentRepo repo){
        this.repo = repo;

    }

    // saves the data send via postman request/client
    @PostMapping
    public Student saveStudent(Student student){
        return repo.save(student);

    }

    // get all students from the database
    @GetMapping
    public List<Student> getAllStudents(){
        return repo.findAll();

    }

    // get data of all from db and pass it to controller
//    @GetMapping
//    public List<Student> NewStudents(){
//        return repo.findAll();
//
//
//    }
}
