package org.b07boys.walnut.courses;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.database.DatabaseNodeEditor;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.CourseAdapter;
import org.b07boys.walnut.user.TakenCourses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class CourseUtils {

    public static Task<Void> createCourse(String code, String name, SessionType[] offeredSessions, String[] prerequisiteUIDS) {

        CourseAdapter courseAdapter = new CourseAdapter(
                code,
                name,
                offeredSessions,
                prerequisiteUIDS
        );

        return new DatabaseNodeEditor(DatabasePaths.COURSES_AVAILABLE.path).writeAsChild("", courseAdapter);
    }

    public static Task<Void> modifyCourse(String uid, String code, String name, SessionType[] offeredSessions, String[] prerequisiteUIDS) {

        CourseAdapter courseAdapter = new CourseAdapter(
                code,
                name,
                offeredSessions,
                prerequisiteUIDS
        );

        return new DatabaseNodeEditor(DatabasePaths.COURSES_AVAILABLE.path).modifyChild("", uid, courseAdapter);
    }

    public static Task<Void> removeCourse(String courseUID) {
        return new DatabaseNodeEditor(DatabasePaths.COURSES_AVAILABLE.path).deleteValue(courseUID);
    }

    public static Task<Void> addTakenCourse(Course course) {

        TakenCourses takenCourses = TakenCourses.getInstance();

        if (!takenCourses.courseExists(course)) {

            StringBuilder builder = concatenateCourses(takenCourses.getCourses());
            builder.append(course.getUID());

            return new DatabaseNodeEditor(DatabasePaths.COURSES_TAKEN.path).writeAsChild("",
                    FirebaseAuth.getInstance().getCurrentUser().getUid(), builder.toString());
        }
        return null;
    }

    public static Task<Void> addTakenCourses(ArrayList<Course> courses) {

        TakenCourses takenCourses = TakenCourses.getInstance();

        StringBuilder builder = concatenateCourses(takenCourses.getCourses());

        for (Course course : courses) {
            if (!takenCourses.courseExists(course)) {
                builder.append(course.getUID()).append(" ");
            }
        }
        return new DatabaseNodeEditor(DatabasePaths.COURSES_TAKEN.path).writeAsChild("",
                FirebaseAuth.getInstance().getCurrentUser().getUid(), builder.toString().trim());

    }

    public static Task<Void> removeTakenCourse(Course course) {

        TakenCourses takenCourses = TakenCourses.getInstance();

        if (takenCourses.courseExists(course)) {

            takenCourses.removeCourse(course);

            StringBuilder builder = concatenateCourses(takenCourses.getCourses());

            return new DatabaseNodeEditor(DatabasePaths.COURSES_TAKEN.path).writeAsChild("",
                    FirebaseAuth.getInstance().getCurrentUser().getUid(), builder.toString().trim());

        }

        return null;
    }

    private static StringBuilder concatenateCourses(Set<Course> courses) {
        StringBuilder builder = new StringBuilder();
        for (Course takenCourse : TakenCourses.getInstance().getCourses()) {
            builder.append(takenCourse.getUID()).append(" ");
        }
        return builder;
    }
}
