package org.b07boys.walnut.database.syncs;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.database.DatabaseNodeEditor;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.DatabaseSingleValueChange;
import org.b07boys.walnut.user.TakenCourses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TakenCoursesSync extends DatabaseSingleValueChange <String> {

    private TakenCourses takenCourses;

    public TakenCoursesSync(TakenCourses takenCourses, String node) {
        super(node, String.class);
        this.takenCourses = takenCourses;
    }

    @Override
    protected void onChange(String uids) {
        if (uids == null) {
            DatabaseNodeEditor nodeEditor = new DatabaseNodeEditor(DatabasePaths.COURSES_TAKEN.path);
            nodeEditor.writeAsChild("",
                    FirebaseAuth.getInstance().getCurrentUser().getUid(), "");
            return;
        }

        List<String> courses = Arrays.asList(uids.split(" "));

        for (Course course : takenCourses.getCourses()) {

            String uid = course.getUID();

            if (!courses.contains(uid)) {
                takenCourses.removeCourse(course);
            }

        }

        for (String uid : courses) {

            Course course = CourseCatalogue.getInstance().getCourseByUID(uid);

            if (course == null) {
                takenCourses.getCoursesNotInitialized().add(uid);
            } else if (!takenCourses.courseExists(course)) {
                    takenCourses.addCourse(course);
            }

        }

    }
}
