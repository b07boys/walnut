package org.b07boys.walnut.courses;

import java.util.HashSet;

public class Session {
    private HashSet<Course> courses;
    private SessionTypeEnum session;

    public Session(HashSet<Course> courses) {
        this.courses = courses;
    }

    public HashSet<Course> getCourses() {
        return courses;
    }

    public void setCourses(HashSet<Course> courses) {
        this.courses = courses;
    }

    public boolean addCourse(Course course) {
        return courses.add(course);
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }
}
