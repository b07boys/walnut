package org.b07boys.walnut.courses;

import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.CourseAdapter;
import org.b07boys.walnut.database.syncs.DatabaseCourseSync;

import java.util.HashSet;

public class CourseCatalogue extends CourseStructure {

    private static CourseCatalogue instance;

    private CourseCatalogue() {
        super(new HashSet<>());
        initSync();
    }

    private void initSync() {
        new DatabaseCourseSync(this, DatabasePaths.COURSES_AVAILABLE.path, CourseAdapter.class)
                .startListening();
    }

    public static CourseCatalogue getInstance() {
        if (instance == null)
            instance = new CourseCatalogue();
        return instance;
    }
}
