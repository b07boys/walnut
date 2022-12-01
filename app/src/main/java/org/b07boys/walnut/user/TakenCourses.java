package org.b07boys.walnut.user;

import org.b07boys.walnut.courses.CourseStructure;

import java.util.HashSet;

public class TakenCourses extends CourseStructure {

    private static TakenCourses instance;

    private TakenCourses() {
        super(new HashSet<>());
    }

    private void initSync() {
        //TODO: sync
    }

    public static TakenCourses getInstance() {
        if (instance == null)
            instance = new TakenCourses();
        return instance;
    }

}
