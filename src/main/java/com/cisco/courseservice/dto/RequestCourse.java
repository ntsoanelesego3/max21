package com.cisco.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCourse {
    private Long id;
    private String title;
    private String description;
    private String language;
    private String level;
    private Integer duration;
}
