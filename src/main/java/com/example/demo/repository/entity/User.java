package com.example.demo.repository.entity;


import com.arangodb.entity.DocumentField;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class User {

    @DocumentField(DocumentField.Type.ID)
    private String id;

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Gender gender;

    public User() {
    }

    public User(String name, LocalDate dateOfBirth, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }
}
