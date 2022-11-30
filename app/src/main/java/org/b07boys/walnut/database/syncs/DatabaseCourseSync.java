package org.b07boys.walnut.database.syncs;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalog;
import org.b07boys.walnut.database.DatabaseNodeSync;
import org.b07boys.walnut.database.adapters.CourseAdapter;

public class DatabaseCourseSync extends DatabaseNodeSync<CourseAdapter> {

    private CourseCatalog courseCatalog;

    public DatabaseCourseSync(String node, Class<CourseAdapter> clazz) {
        super(node, clazz);
        courseCatalog = CourseCatalog.getInstance();
    }

    @Override
    protected void childAdded(CourseAdapter courseAdapter, String key) {
        Course course = courseAdapter.createCourse(key);
        courseCatalog.addCourse(course);
    }

    @Override
    protected void childChanged(CourseAdapter courseAdapter, String key) {
        Course course = courseCatalog.getCourseByUID(key);
        if (course == null)
            childAdded(courseAdapter, key);
        else
            courseAdapter.mapToCourse(course);
    }

    @Override
    protected void childRemoved(CourseAdapter object, String key) {
        courseCatalog.removeCourseByUID(key);
    }

    @Override
    protected void childMoved(CourseAdapter object, String key) {
        // TODO: if implementing ordering, do it here
    }
}
