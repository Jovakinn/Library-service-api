package com.example.library.models;

public record Author(Integer id, String firstName, String lastName) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
