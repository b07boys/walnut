package org.b07boys.walnut;

import java.util.Arrays;
import java.util.Objects;

public class Course {
    enum Session{FALL, WINTER, SUMMER}
    private Course[] prerequisites;
    private String name;
    private String code;
    private Session[] offering_sessions;

    public Course(String code, String name, Session[] offering_sessions, Course[] prerequisites) {
        this.prerequisites = prerequisites;
        this.name = name;
        this.code = code;
        this.offering_sessions = offering_sessions;
    }

    public Course[] getPrerequisites() {
        return prerequisites;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Session[] getOffering_sessions() {
        return offering_sessions;
    }

    public void setPrerequisites(Course[] prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOffering_sessions(Session[] offering_sessions) {
        this.offering_sessions = offering_sessions;
    }

    @Override
    public String toString() {
        return code + ": " + name +
                "\nPrerequisites: " + Arrays.toString(prerequisites) +
                "\nOffered in: " + Arrays.toString(offering_sessions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
