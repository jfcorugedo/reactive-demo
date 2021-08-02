package com.jfcorugedo.reactivedemo.people.model;

import org.springframework.data.annotation.Id;

public class Person {

    @Id
    private final String id;
    private final String name;
    private final int age;

    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
