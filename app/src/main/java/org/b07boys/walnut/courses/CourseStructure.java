package org.b07boys.walnut.courses;

import java.util.Set;

public class CourseStructure {

    private final Set<Course> courses;

    public CourseStructure(Set<Course> courses){
        this.courses = courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public boolean removeCourse(Course course){
        return courses.remove(course);
    }

    public boolean removeCourseByUID(String uid) {
        return removeCourse(getCourseByUID(uid));
    }

    public Set<Course> getCourses(){
        return courses;
    }


    public Course getCourseByUID(String uid) {
        for (Course course : courses) {
            if (course.getUID().equals(uid))
                return course;
        }
        return null;
    }

    /**
     * Checks whether a course is already present in the catalog.
     *
     * @return true if course already exits, false otherwise.
     */
    public boolean courseExists(Course target){
        for (Course course : courses) {
            if (course.getUID().equals(target.getUID()))
                return true;
        }
        return false;
    }

}
