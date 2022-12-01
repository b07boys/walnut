package org.b07boys.walnut.user;

import org.b07boys.walnut.timeline.ValidTimelines;

public class User {

    private static User instance;

    private final TakenCourses takenCourses;

    private final ValidTimelines validTimelines;

    private User() {
        takenCourses = TakenCourses.getInstance();
        validTimelines = ValidTimelines.getInstance();
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

    public ValidTimelines getValidTimelines(){
        return validTimelines;
    }
}