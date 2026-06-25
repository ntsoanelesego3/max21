package com.cisco.studnetservice.Service;

import com.cisco.studnetservice.dao.Repo;
import com.cisco.studnetservice.dto.CourseResponse;
import com.cisco.studnetservice.dto.RequestStudent;
import com.cisco.studnetservice.fegin.studentInterface;
import com.cisco.studnetservice.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudnetService {

    @Autowired
    private Repo repo;

    @Autowired
    private studentInterface studentInterface;


    // add new students
    public Student addStudent(RequestStudent requestStudent){
        if (repo.existsByEmail(requestStudent.getEmail())){
            throw new RuntimeException("Email already exist" + requestStudent.getEmail());
        }

        Student student = new Student();

        student.setFirstName(requestStudent.getFirstName());
        student.setLastName(requestStudent.getLastName());
        student.setEmail(requestStudent.getEmail());
        student.setPhone(requestStudent.getPhone());

        return repo.save(student);
    }

    // get all students
    public List<Student> getAllStudents(){
        return repo.findAll();
    }


    // remove students
    public ResponseEntity<String> removeStudent(Long id){
        repo.deleteById(id);
        return new ResponseEntity<>("deleted", HttpStatus.CREATED);
    }


    public ResponseEntity<String> updateStudent(Long id, RequestStudent requestStudent){
        Student student1 = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("student not found " + id));

        student1.setFirstName(requestStudent.getFirstName());
        student1.setLastName(requestStudent.getLastName());
        student1.setPhone(requestStudent.getPhone());
        student1.setEmail(requestStudent.getEmail());
        repo.save(student1);

        return new ResponseEntity<>("updated", HttpStatus.ACCEPTED);


    }


    // course-service
    public List<CourseResponse> getAllAviableCourse(){
        return studentInterface.getCourses();
    }

    public CourseResponse getCourseTitle(String title){
        return studentInterface.getCourseByTitle(title);

    }

    public List<CourseResponse> getCourseLanguage(String language){
        return studentInterface.getCourseByLanguage(language);
    }




}
