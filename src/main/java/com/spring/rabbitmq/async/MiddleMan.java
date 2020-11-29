package com.spring.rabbitmq.async;

abstract public class MiddleMan extends Parent {

    abstract String getName();

    public String getData() {
        return getName();
    }

}
