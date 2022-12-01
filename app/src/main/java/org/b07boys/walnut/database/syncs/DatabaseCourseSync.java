package org.b07boys.walnut.database.syncs;

import android.util.Log;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseStructure;
import org.b07boys.walnut.database.DatabaseNodeSync;
import org.b07boys.walnut.database.adapters.CourseAdapter;

public class DatabaseCourseSync extends DatabaseNodeSync<CourseAdapter> {

    private CourseStructure courseStructure;

    public DatabaseCourseSync(CourseStructure courseStructure, String node, Class<CourseAdapter> clazz) {
        super(node, clazz);
        this.courseStructure = courseStructure;
    }

    @Override
    protected void childAdded(CourseAdapter courseAdapter, String key) {
        Course course = courseAdapter.createCourse(key);
        courseStructure.addCourse(course);
        //test
        Log.v("ADD_COURSE", course.toString());
    }

    @Override
    protected void childChanged(CourseAdapter courseAdapter, String key) {
        Course course = courseStructure.getCourseByUID(key);
        if (course == null)
            childAdded(courseAdapter, key);
        else
            courseAdapter.mapToCourse(course);
        //test
        Log.v("CHANGE_COURSE", courseAdapter.createCourse(key).toString());
    }

    @Override
    protected void childRemoved(CourseAdapter courseAdapter, String key) {
        courseStructure.removeCourseByUID(key);
        Log.v("REMOVE_COURSE", "uid: " + key);
    }

}
