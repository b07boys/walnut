package org.b07boys.walnut.database.syncs;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.database.DatabaseNodeSync;
import org.b07boys.walnut.database.adapters.TakenCoursesAdapter;
import org.b07boys.walnut.user.TakenCourses;

public class TakenCoursesSync extends DatabaseNodeSync <TakenCoursesAdapter> {

    private TakenCourses takenCourses;
    private CourseCatalogue courseCatalgoue;

    public TakenCoursesSync(TakenCourses takenCourses, String node, Class<TakenCoursesAdapter> clazz) {
        super(node, clazz);
        this.takenCourses = takenCourses;
        courseCatalgoue = CourseCatalogue.getInstance();
    }

    @Override
    protected void childAdded(TakenCoursesAdapter course, String key) {
        for (String uid : course.getCourses()) {
            Course courseTarget = courseCatalgoue.getCourseByUID(uid);
            if (courseTarget == null) {
                takenCourses.getCoursesNotInitialized().add(uid);
            } else {
                takenCourses.addCourse(courseTarget);
            }
        }
    }

    @Override
    protected void childChanged(TakenCoursesAdapter course, String key) {
        takenCourses.getCourses().clear();
        childAdded(course, key);
    }

    @Override
    protected void childRemoved(TakenCoursesAdapter course, String key) {
        takenCourses.getCourses().clear();
    }


}
