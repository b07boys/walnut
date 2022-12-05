package org.b07boys.walnut.user;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseStructure;
import org.b07boys.walnut.courses.ModifyCourseType;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.syncs.TakenCoursesSync;

import java.util.HashSet;
import java.util.Set;

public class TakenCourses extends CourseStructure {

    private static TakenCourses instance;
    private Set <String> coursesNotInitialized;

    private TakenCourses() {
        super(new HashSet<>());
        coursesNotInitialized = new HashSet<>();
        initializeCourses();
        initSync();
    }

    private void initSync() {
        new TakenCoursesSync(this, DatabasePaths.COURSES_TAKEN.path
                + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                .startListening();
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

        CourseCatalogue.getInstance().registerListener((course, modifyType) -> {
            if (modifyType == ModifyCourseType.ADD) {
                String courseUID = course.getUID();
                if (coursesNotInitialized.contains(courseUID)) {
                    coursesNotInitialized.remove(courseUID);
                    if (!courseExists(course))
                        addCourse(course);
                }
            }
        });

    }

}
