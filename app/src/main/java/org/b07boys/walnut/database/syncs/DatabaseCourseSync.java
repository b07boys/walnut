package org.b07boys.walnut.database.syncs;

import android.util.Log;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalog;
import org.b07boys.walnut.database.DatabaseNodeSync;
import org.b07boys.walnut.database.adapters.CourseAdapter;

public class DatabaseCourseSync extends DatabaseNodeSync<CourseAdapter> {

    private CourseCatalog courseCatalog;

    public DatabaseCourseSync(CourseCatalog courseCatalog, String node, Class<CourseAdapter> clazz) {
        super(node, clazz);
        this.courseCatalog = courseCatalog;
    }

    @Override
    protected void childAdded(CourseAdapter courseAdapter, String key) {
        Course course = courseAdapter.createCourse(key);
        courseCatalog.addCourse(course);
        //test
        Log.v("ADD_COURSE", course.toString());
    }

    @Override
    protected void childChanged(CourseAdapter courseAdapter, String key) {
        Course course = courseCatalog.getCourseByUID(key);
        if (course == null)
            childAdded(courseAdapter, key);
        else
            courseAdapter.mapToCourse(course);
        //test
        Log.v("CHANGE_COURSE", courseAdapter.createCourse(key).toString());
    }

    @Override
    protected void childRemoved(CourseAdapter courseAdapter, String key) {
        courseCatalog.removeCourseByUID(key);
        Log.v("REMOVE_COURSE", "uid: " + key);
    }

}
