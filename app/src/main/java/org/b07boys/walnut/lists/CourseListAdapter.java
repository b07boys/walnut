package org.b07boys.walnut.lists;

import org.b07boys.walnut.courses.Course;

public class CourseListAdapter {
    private Course course;

    @Override
    public String toString() {
        return course.getCode() + " " + course.getName();
    }

    public CourseListAdapter(Course course){
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
