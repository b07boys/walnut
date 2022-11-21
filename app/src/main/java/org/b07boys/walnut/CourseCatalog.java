package org.b07boys.walnut;

import java.util.ArrayList;
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


}
