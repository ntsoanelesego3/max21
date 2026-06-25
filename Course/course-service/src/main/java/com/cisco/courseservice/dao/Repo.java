package com.cisco.courseservice.dao;

import com.cisco.courseservice.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Repo extends MongoRepository<Course, String> {
    boolean existsCourseByTitle(String title);

    Course deleteCourseByTitle(String title);

    Course findCourseByTitle(String title);

    List<Course> findCourseByLanguage(String language);

}
