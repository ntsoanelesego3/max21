package com.example.demo;

import com.example.demo.dao.StudentRepo;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // saved data from the service layer is displayed in database
    @PostMapping(path = "/student", consumes = {"application/json"})
    public Student createStudent(@RequestBody Student student){
        return service.saveStudent(student);

    }

    // the data sent is retrivaled from the database
    @GetMapping(path = "/students", produces = {"application/json"})
    public List<Student> getStudents(){
        return service.getAllStudents();

    }

//    delect the data using id
    @DeleteMapping("/StudentRemoved/{id}")
    public String removeStudent(@PathVariable("id") int id){
        return service.delectStudent(id);
    }

    // update the student record in the database
    @PutMapping("/update/{id}")
    public Student NewStudentNumber(@PathVariable("id") int id , @RequestBody Student student){
       return service.updateStudent(id,student);
    }


    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    // get students from the service layer hardcoded data
//    @GetMapping("/students")
//    public List<Student> getStudent(){
//        return service.getStudent();
//
//
//    }
//
//    // get all students from the database
//    @GetMapping("/findall")
//    public List<Student> findAll(){
//        return service.NewStudents();
//    }
}
