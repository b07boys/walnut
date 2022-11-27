package org.b07boys.walnut.courses;

import java.util.HashSet;

public class CourseCatalog {
    private static CourseCatalog catalog;

    private static HashSet<Course> courses;

    private CourseCatalog(){
        courses = new HashSet<>();
    }

    public static CourseCatalog getInstance()
    {
        if (catalog == null)
            catalog = new CourseCatalog();

        return catalog;
    }

    public static boolean addCourse(Course course){
        return courses.add(course);
    }

    public static boolean removeCourse(Course course){
        return courses.remove(course);
    }

    public static HashSet<Course> getCourses(){
        return courses;
    }

    /**
     * Checks whether a course is already present in the catalog.
     *
     * @return true if course already exits, false otherwise.
     */
    public static boolean checkCourse(){
        //TODO
        return false;
    }

}
