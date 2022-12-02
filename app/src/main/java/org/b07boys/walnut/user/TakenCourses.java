package org.b07boys.walnut.user;

import org.b07boys.walnut.courses.CourseStructure;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.TakenCoursesAdapter;
import org.b07boys.walnut.database.syncs.TakenCoursesSync;

import java.util.HashSet;
import java.util.Set;

public class TakenCourses extends CourseStructure {

    private static TakenCourses instance;
    private Set <String> coursesNotInitialized;

    private Set<String> coursesNotInitialized;

    private TakenCourses() {
        super(new HashSet<>());
        coursesNotInitialized = new HashSet<>();
        initSync();
    }

    private void initSync() {
        new TakenCoursesSync(this, DatabasePaths.COURSES_TAKEN.path, TakenCoursesAdapter.class)
                .startListening();
    }

    public Set<String> getCoursesNotInitialized() {
        return coursesNotInitialized;
    }

    public Set<String> getCoursesNotInitialized() {
        return coursesNotInitialized;
    }

    public static TakenCourses getInstance() {
        if (instance == null)
            instance = new TakenCourses();
        return instance;
    }

    private void initializeCourses() {

    }

}
