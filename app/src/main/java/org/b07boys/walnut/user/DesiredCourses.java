package org.b07boys.walnut.user;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseStructure;

import java.util.HashSet;
import java.util.Set;

public class DesiredCourses extends CourseStructure {

    private static DesiredCourses instance;

    private DesiredCourses() {
        super(new HashSet<>());
        instance = this;
    }

    public static DesiredCourses getInstance() {
        if (instance == null) {
            instance = new DesiredCourses();
        }
        return instance;
    }

}
