package org.b07boys.walnut.courses;

import org.b07boys.walnut.database.DatabaseNodeEditor;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.CourseAdapter;

public class CourseUtils {

    public static void createCourse(String code, String name, SessionType[] offeredSessions, String[] prerequisiteUIDS) {

        CourseAdapter courseAdapter = new CourseAdapter(
                code,
                name,
                offeredSessions,
                prerequisiteUIDS
        );

        new DatabaseNodeEditor(DatabasePaths.COURSES_AVAILABLE.path).writeAsChild("", courseAdapter);

    }

    public static void modifyCourse(String uid, String code, String name, SessionType[] offeredSessions, String[] prerequisiteUIDS) {

        CourseAdapter courseAdapter = new CourseAdapter(
                code,
                name,
                offeredSessions,
                prerequisiteUIDS
        );

        new DatabaseNodeEditor(DatabasePaths.COURSES_AVAILABLE.path).modifyChild("", uid, courseAdapter);
    }

    public static void removeCourse(String courseUID) {
        new DatabaseNodeEditor(DatabasePaths.COURSES_AVAILABLE.path).deleteValue(courseUID);
    }

}
