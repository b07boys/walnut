package org.b07boys.walnut.courses;

import java.util.HashSet;

public class Session {
    private HashSet<Course> courses;
    private SessionTypeEnum.SessionType session;

    public Session(HashSet<Course> courses, SessionTypeEnum.SessionType session) {
        this.courses = courses;
        this.session = session;
    }

    public HashSet<Course> getCourses() {
        return courses;
    }

    public void setCourses(HashSet<Course> courses) {
        this.courses = courses;
    }

    public SessionTypeEnum.SessionType getSession() {
        return session;
    }

    public void setSession(SessionTypeEnum.SessionType session) {
        this.session = session;
    }

    public boolean addCourse(Course course) {
        return courses.add(course);
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }
}
