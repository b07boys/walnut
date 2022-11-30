package org.b07boys.walnut.courses;

import java.util.HashSet;

public class CourseCatalog {

    private static CourseCatalog catalog;

    private HashSet<Course> courses;

    private CourseCatalog(){
        courses = new HashSet<>();
    }

    public static CourseCatalog getInstance()
    {
        if (catalog == null)
            catalog = new CourseCatalog();

        return catalog;
    }

    public boolean addCourse(Course course){
        return courses.add(course);
    }

    public boolean removeCourse(Course course){
        return courses.remove(course);
    }

    public HashSet<Course> getCourses(){
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
