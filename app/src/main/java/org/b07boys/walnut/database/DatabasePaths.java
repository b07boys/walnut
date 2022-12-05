package org.b07boys.walnut.database;

public enum DatabasePaths {

    ADMINS("admins"),
    COURSES_AVAILABLE("courses_available"),
    COURSES_TAKEN("courses_taken");
    public final String path;

    DatabasePaths(String path) {
        this.path = path;
    }

}
