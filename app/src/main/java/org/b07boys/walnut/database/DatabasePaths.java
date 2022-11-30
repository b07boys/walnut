package org.b07boys.walnut.database;

public enum DatabasePaths {

    ADMINS("admins"),
    COURSES_AVAILABLE("courses_available"),
    TIMELINES("timelines");

    public final String path;

    private DatabasePaths(String path) {
        this.path = path;
    }

}
