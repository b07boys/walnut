package org.b07boys.walnut.database.adapters;

import org.b07boys.walnut.courses.Course;

public class CourseListAdapter {
    private Course course;

    @Override
    public String toString() {
        return course.getCode();
    }

    public CourseListAdapter(Course course){
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
