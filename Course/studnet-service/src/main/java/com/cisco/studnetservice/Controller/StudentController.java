package com.cisco.studnetservice.Controller;

import com.cisco.studnetservice.Service.StudnetService;
import com.cisco.studnetservice.dto.CourseResponse;
import com.cisco.studnetservice.dto.RequestStudent;
import com.cisco.studnetservice.models.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private StudnetService service;

    public StudentController(StudnetService service) {
        this.service = service;
    }

    @PostMapping(path = "/addStudent", consumes = {"application/json"})
    public Student addedStudent(@RequestBody RequestStudent requestStudent){
        return service.addStudent(requestStudent);

    }


    @GetMapping(path = "/allStudent", produces = {"application/json"})
    public List<Student> allStudent(){
        return service.getAllStudents();
    }


    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id){
        return service.removeStudent(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatedStudents(@PathVariable("id") Long id,
                                                  @RequestBody RequestStudent requestStudent){
        return service.updateStudent(id, requestStudent);

    }


    // course-service

    @PostMapping("/allMyCourses")
    public ResponseEntity<List<CourseResponse>> allcourse(){
        return ResponseEntity.ok(service.getAllAviableCourse());
    }

    @GetMapping("/course/title/{title}")
    public ResponseEntity<CourseResponse> getCourseByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok(service.getCourseTitle(title));
    }

    @GetMapping("/course/language/{language}")
    public ResponseEntity<List<CourseResponse>> getCourseByLanguage(@PathVariable("language") String language){
        return ResponseEntity.ok(service.getCourseLanguage(language));
    }
}
