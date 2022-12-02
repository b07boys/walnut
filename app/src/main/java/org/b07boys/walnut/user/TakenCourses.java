package org.b07boys.walnut.user;

import org.b07boys.walnut.courses.CourseStructure;

import java.util.HashSet;
import java.util.Set;

public class TakenCourses extends CourseStructure {

    private static TakenCourses instance;

    private Set<String> coursesNotInitialized;

    private TakenCourses() {
        super(new HashSet<>());
        coursesNotInitialized = new HashSet<>();
    }

    private void initSync() {
        //TODO: sync
    }

    public Set<String> getCoursesNotInitialized() {
        return coursesNotInitialized;
    }

    public static TakenCourses getInstance() {
        if (instance == null)
            instance = new TakenCourses();
        return instance;
    }

}
