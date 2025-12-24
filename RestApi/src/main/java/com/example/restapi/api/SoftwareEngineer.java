package com.example.restapi.api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Entity
public class SoftwareEngineer {
    @Id
    private int id;
    @Column(name = "nmme")
    private String name;

    @Column(name = "tech_stack")
    private String techStack;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechStack() {
        return techStack;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    @Override
    public String toString() {
        return "SoftwareEngineer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", techStack='" + techStack + '\'' +
                '}';
    }
}
