package org.b07boys.walnut.courses;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Objects;

public class Course {
    private Object UID;
    private Course[] prerequisites;
    private String name;
    private String code;
    private SessionType[] offeringSessions;

    public Course(String code, String name, SessionType[] offeringSessions,
                  Course[] prerequisites) {
        //TODO generate UID @Lucas
        this.prerequisites = prerequisites;
        this.name = name;
        this.code = code;
        this.offeringSessions = offeringSessions;
    }

    public Object getUID() {
        return UID;
    }

    public Course[] getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Course[] prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SessionType[] getOfferingSessions() {
        return offeringSessions;
    }

    public void setOfferingSessions(SessionType[] offeringSessions) {
        this.offeringSessions = offeringSessions;
    }

    @NonNull
    @Override
    public String toString() {
        return code + ": " + name +
                "\nPrerequisites: " + Arrays.toString(prerequisites) +
                "\nOffered in: " + Arrays.toString(offeringSessions);
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
