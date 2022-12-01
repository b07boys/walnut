package org.b07boys.walnut.courses;

public enum SessionType {
    FALL(0),
    WINTER(1),
    SUMMER(2),
    INVALID(-1);

    public final int id;

    SessionType(int id) {
        this.id = id;
    }
}

