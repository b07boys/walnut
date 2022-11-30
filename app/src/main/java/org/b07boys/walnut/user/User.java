package org.b07boys.walnut.user;

import org.b07boys.walnut.courses.Course;

import java.util.ArrayList;

public class User {

    private static User instance;

    private ArrayList<Course> takenCourses;

    private User() {
        takenCourses = new ArrayList<>();
        populateFields();
    }

    private void populateFields() {
        //TODO: Take from db and populate everything
    }

    public static User getInstance() {
        if (instance == null)
            instance = new User();
        return instance;
    }

    public ArrayList<Course> getTakenCourses() {
        return takenCourses;
    }
}