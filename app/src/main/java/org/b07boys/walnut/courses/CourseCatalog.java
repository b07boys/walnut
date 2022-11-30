package org.b07boys.walnut.courses;

import android.util.Log;

import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.CourseAdapter;
import org.b07boys.walnut.database.syncs.DatabaseCourseSync;

import java.util.HashSet;

public class CourseCatalog {

    private static CourseCatalog catalog;

    private HashSet<Course> courses;
    private DatabaseCourseSync databaseCourseSync;

    private CourseCatalog(){
        courses = new HashSet<>();
        databaseCourseSync = new DatabaseCourseSync(this, DatabasePaths.COURSES_AVAILABLE.path, CourseAdapter.class);
        databaseCourseSync.startListening();
    }

    public static CourseCatalog getInstance()
    {
        if (catalog == null)
            catalog = new CourseCatalog();

        return catalog;
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
