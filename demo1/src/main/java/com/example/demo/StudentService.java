package com.example.demo;

import com.example.demo.dao.StudentRepo;
import com.example.demo.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // delete  all student
    @DeleteMapping
    public String delectStudent(@RequestParam int id){
        repo.deleteById(id);
        return "delected";

    }

    /* update data
    findById(id) finds the student
     Optional contains a value(Student records) or null/empty(records does not exist)
     orElseThrow() returns the Student or throws exception
     */

    @PutMapping
    public Student updateStudent(int id, Student updateMyStudent) {
        Student student = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + id)
                );
        student.setFirst_name(updateMyStudent.getFirst_name());
        student.setLast_name(updateMyStudent.getLast_name());
        student.setStudent_number(updateMyStudent.getStudent_number());
        return  repo.save(student);
    }


    // get data of all from db and pass it to controller
//    @GetMapping
//    public List<Student> NewStudents(){
//        return repo.findAll();
//
//
//    }
}
