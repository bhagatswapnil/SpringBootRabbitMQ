package com.spring.rabbitmq.async;

public class Person {

    private String firstName;

    public String getLastName() {
        return lastName;
    }

    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}