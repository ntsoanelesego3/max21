package com.cisco.courseservice.Controller;

import com.cisco.courseservice.Service.CourseService;
import com.cisco.courseservice.dto.RequestCourse;
import com.cisco.courseservice.models.Course;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/allCourse", produces = {"application/json"})
    public List<Course> getCourses(){
        return courseService.getAllCourse();
    }

    @PostMapping(path = "/addCourse", consumes = {"application/json"})
    public Course addCourse(@RequestBody RequestCourse requestCourse){
       return courseService.addNewCourse(requestCourse);
    }

    @DeleteMapping(path = "/delete/{title}")
    public Course deleteCourseByTitle(@PathVariable("title") String title){
        return courseService.deleteCourse(title);
    }

    @GetMapping("/findCourse/{title}")
    public Course getCourseByTitle(@PathVariable("title") String title){
        return courseService.findByTitle(title);
    }

    @GetMapping("/language/{language}")
    public List<Course> getCourseByLanguage(@PathVariable("language") String language){
        return courseService.getCourseByLanguage(language);

    }

}
