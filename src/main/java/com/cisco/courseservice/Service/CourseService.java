package com.cisco.courseservice.Service;

import com.cisco.courseservice.dao.Repo;
import com.cisco.courseservice.dto.RequestCourse;
import com.cisco.courseservice.models.Course;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    Repo repo;

    @Autowired
    Environment environment;

    private OllamaService ollamaService;


    public List<Course> getAllCourse(){
        System.out.println(environment.getProperty("local.server.port"));
        return repo.findAll();
    }


    /* 1.User add course
    2.Title is give to AI model(ollama local model)
    3.AI model gives description
    4.description added to db
     */

    public Course addNewCourse(@RequestBody RequestCourse requestCourse){
        if(repo.existsCourseByTitle(requestCourse.getTitle())){
            throw new RuntimeException("Course already exist");
        }
        System.out.println(environment.getProperty("local.server.port"));



        Course course = new Course();

        course.setTitle(requestCourse.getTitle());

        // Generate description via Ollama instead of using request body
        String aiDescription = ollamaService.generateDescription(
                requestCourse.getTitle(), requestCourse.getLanguage());

        course.setDescription(aiDescription);

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
       return repo.findCourseByTitle(title);

    }

    public List<Course> getCourseByLanguage(@PathVariable("language") String language){
        System.out.println(environment.getProperty("local.server.port"));
       return repo.findCourseByLanguage(language);


    }

}
