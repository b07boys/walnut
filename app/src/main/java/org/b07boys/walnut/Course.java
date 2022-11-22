package org.b07boys.walnut;

import java.util.Arrays;
import java.util.Objects;

public class Course {
    enum Session{FALL, WINTER, SUMMER}
    private Course[] prerequisites;
    private String name;
    private String code;
    private Session[] offeringSessions;

    public Course(String code, String name, Session[] offeringSessions, Course[] prerequisites) {
        this.prerequisites = prerequisites;
        this.name = name;
        this.code = code;
        this.offeringSessions = offeringSessions;
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

    public Session[] getOfferingSessions() {
        return offeringSessions;
    }

    public void setPrerequisites(Course[] prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOfferingSessions(Session[] offeringSessions) {
        this.offeringSessions = offeringSessions;
    }

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
