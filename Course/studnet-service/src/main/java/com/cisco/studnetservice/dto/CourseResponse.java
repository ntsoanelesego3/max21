package com.cisco.studnetservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CourseResponse {

    private String title;
    private String description;
    private String language;
    private String level;
    private Integer durationHours;
}

