package org.b07boys.walnut.courses;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private List<Course> courses;
    private SessionType session;

    public Session(List<Course> courses, SessionType session) {
        this.courses = courses;
        this.session = session;
    }

    public Session(SessionType session){
        this.session = session;
        courses = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


    public SessionType getSessionType() {
        return session;
    }

    public void setSessionType(SessionType session) {
        this.session = session;
    }

    public boolean addCourse(Course course) {
        return courses.add(course);
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }
}
