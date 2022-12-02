package org.b07boys.walnut;

import org.b07boys.walnut.courses.Course;

import java.util.Objects;

public class CourseModel {
    private Course course;
    private boolean isChecked;

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModel that = (CourseModel) o;
        return course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course);
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean getChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
