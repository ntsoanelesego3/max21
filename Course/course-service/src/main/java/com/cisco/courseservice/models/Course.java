package com.cisco.courseservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private String title;
    private String description;
    private String language;
    private String level;
    private Integer durationHours;
}
