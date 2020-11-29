package com.spring.rabbitmq.async;

import org.springframework.stereotype.Component;

@Component
public abstract class Parent {

    abstract String getData();

    public void process() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId() + " " + this.getData());
    }

}