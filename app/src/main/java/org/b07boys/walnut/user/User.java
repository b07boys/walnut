package org.b07boys.walnut.user;

public class User {

    private static User instance;

    private TakenCourses takenCourses;

    private User() {
        takenCourses = TakenCourses.getInstance();
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

    public TakenCourses getTakenCourses() {
        return takenCourses;
    }
}