package com.clean.code.springboot56.model;

public class Student {
    private int id;
    private String name;
    private String last_name;
    private String course;

    public Student(int id, String name, String last_name, String course) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
