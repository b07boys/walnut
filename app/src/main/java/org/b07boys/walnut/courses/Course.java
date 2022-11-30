package org.b07boys.walnut.courses;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Objects;

public class Course {
    private String uid;
    private String[] prerequisiteUIDS;
    private String name;
    private String code;
    private SessionType[] offeringSessions;

    public Course(String uid, String code, String name, SessionType[] offeringSessions,
                  String[] prerequisiteUIDS) {
        this.uid = uid;
        this.prerequisiteUIDS = prerequisiteUIDS;
        this.name = name;
        this.code = code;
        this.offeringSessions = offeringSessions;
    }

    public String getUID() {
        return uid;
    }

    public String[] getPrerequisiteUIDS() {
        return prerequisiteUIDS;
    }

    public void setPrerequisiteUIDS(String[] prerequisiteUIDS) {
        this.prerequisiteUIDS = prerequisiteUIDS;
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

        CourseCatalogue courseCatalog = CourseCatalogue.getInstance();

        StringBuilder builder = new StringBuilder();

        builder.append("UID: ")
                .append(uid)
                .append("\n")
                .append(code)
                .append(": ")
                .append(name)
                .append("\nPrerequisites: ");

        for (String prereqUID : prerequisiteUIDS) {
            Course course = courseCatalog.getCourseByUID(prereqUID);
            builder.append(course != null ? course.getCode() : prereqUID)
                    .append(" ");
        }

        builder.append("\nOffered in: ")
                .append(Arrays.toString(offeringSessions));

        return builder.toString();
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
