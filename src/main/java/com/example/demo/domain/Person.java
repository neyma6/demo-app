package com.example.demo.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Person {

    private final String name;
    private final LocalDate dateOfBirth;
    private final String gender;

    public Person(String name, @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth, String gender) {
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

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", gender='" + gender + '\'' +
            '}';
    }
}
