package com.example.demo.repository.entity;

public enum Gender {
    MALE("male"), FEMALE("female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public static Gender find(String gender) {
        for (Gender g : values()) {
            if (g.getGender().equals(gender)) {
                return g;
            }
        }
        return MALE;
    }
}
