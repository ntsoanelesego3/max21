package com.cisco.studnetservice.fegin;

import com.cisco.studnetservice.dto.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("COURSE-SERVICE")
public interface studentInterface {
    @GetMapping(path = "/course/allCourse", produces = {"application/json"})
    List<CourseResponse> getCourses();

    @GetMapping("/course/language/{language}")
    List<CourseResponse> getCourseByLanguage(@PathVariable("language") String language);

    @GetMapping("/course/findCourse/{title}")
    CourseResponse getCourseByTitle(@PathVariable("title") String title);
}
