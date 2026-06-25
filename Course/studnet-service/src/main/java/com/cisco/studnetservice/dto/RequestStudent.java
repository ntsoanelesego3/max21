package com.cisco.studnetservice.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RequestStudent {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;

}
