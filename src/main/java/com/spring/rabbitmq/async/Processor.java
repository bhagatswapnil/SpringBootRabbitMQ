package com.spring.rabbitmq.async;

import org.springframework.stereotype.Component;

@Component
public class Processor extends MiddleMan {

    private Person person;

    public void initialize(Person person) {
        this.person = person;
    }

    public String getName() {
        return "Name = " + this.person.getFirstName() + " " + this.person.getLastName();
    }

}