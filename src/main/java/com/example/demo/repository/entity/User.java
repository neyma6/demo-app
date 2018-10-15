package com.example.demo.repository.entity;


import com.arangodb.entity.DocumentField;

import java.util.Date;

public class User {

    @DocumentField(DocumentField.Type.ID)
    private String id;

    private String name;
    private Date dateOfBirth;
    private Gender gender;

    public User() {
    }

    public User(String name, Date dateOfBirth, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }
}
