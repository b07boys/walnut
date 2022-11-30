package org.b07boys.walnut.courses;

import com.google.android.gms.tasks.Task;

import org.b07boys.walnut.database.DatabaseNodeEditor;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.CourseAdapter;

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

}
