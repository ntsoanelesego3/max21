package com.cisco.courseservice.Service;

import com.cisco.courseservice.dao.Repo;
import com.cisco.courseservice.dto.RequestCourse;
import com.cisco.courseservice.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    Repo repo;

    @Autowired
    Environment environment;


    public List<Course> getAllCourse(){
        System.out.println(environment.getProperty("local.server.port"));
        return repo.findAll();
    }


    public Course addNewCourse(@RequestBody RequestCourse requestCourse){
        if(repo.existsCourseByTitle(requestCourse.getTitle())){
            throw new RuntimeException("Course already exist");
        }
        System.out.println(environment.getProperty("local.server.port"));

        Course course = new Course();

        course.setTitle(requestCourse.getTitle());
        course.setDescription(requestCourse.getDescription()); // use AI here later
        course.setLanguage(requestCourse.getLanguage());
        course.setLevel(requestCourse.getLevel());
        course.setDurationHours(requestCourse.getDuration());

       return repo.save(course);


    }

    public Course deleteCourse(@PathVariable("title") String title){
        System.out.println(environment.getProperty("local.server.port"));
       return repo.deleteCourseByTitle(title);

    }

    public Course findByTitle(@PathVariable("title") String title){
        System.out.println(environment.getProperty("local.server.port"));
        Course course = repo.findCourseByTitle(title);
        return course;
    }

    public List<Course> getCourseByLanguage(@PathVariable("language") String language){
        System.out.println(environment.getProperty("local.server.port"));
       return repo.findCourseByLanguage(language);


    }

}
