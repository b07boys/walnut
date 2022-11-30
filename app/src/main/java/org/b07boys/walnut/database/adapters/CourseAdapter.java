package org.b07boys.walnut.database.adapters;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.database.DatabaseAdapter;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class CourseAdapter implements DatabaseAdapter {

    public String code;
    public String name;
    public String offeringSessions;
    public String prerequisites;

    public CourseAdapter() {
        // this constructor is required for firebase parsing
    }

    public CourseAdapter(String code, String name, SessionType[] offeringSessions, String[] prerequisites) {

        this.code = code;
        this.name = name;
        this.offeringSessions = convertSessionArrayToString(offeringSessions);
        this.prerequisites = convertUIDArrayToString(prerequisites);

    }

    @Exclude
    public Map<String, Object> toMap() {

        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("name", name);
        result.put("offeringSessions", offeringSessions);
        result.put("prerequisites", prerequisites);

        return result;
    }

    @Exclude
    public void mapToCourse(Course course) {
        course.setCode(code);
        course.setName(name);
        course.setOfferingSessions(convertSessionStringToArray(offeringSessions));
        course.setPrerequisiteUIDS(convertUIDStringToArray(prerequisites));
    }

    @Exclude
    public Course createCourse(String uid) {
        return new Course(
                uid,
                code,
                name,
                convertSessionStringToArray(offeringSessions),
                convertUIDStringToArray(prerequisites)
        );
    }

    @Exclude
    private String convertSessionArrayToString(SessionType[] sessionTypes) {

        StringBuilder builder = new StringBuilder();

        for (SessionType sessionType : sessionTypes)
            builder.append(sessionType.name()).append(" ");

        return builder.toString().trim();
    }

    @Exclude
    private String convertUIDArrayToString(String[] uidArray) {

        StringBuilder builder = new StringBuilder();

        for(String uid : uidArray)
            builder.append(uid).append(" ");

        return builder.toString().trim();
    }

    @Exclude
    private SessionType[] convertSessionStringToArray(String sessionString) {

        String[] splitString = sessionString.split(" ");

        int length = splitString.length;

        SessionType[] sessionArray = new SessionType[length];

        for (int i = 0; i < length; i++) {
            try {
                sessionArray[i] = SessionType.valueOf(splitString[i]);
            } catch (IllegalArgumentException ignored) {
                sessionArray[i] = SessionType.INVALID;
            }
        }

        return sessionArray;
    }

    @Exclude
    private String[] convertUIDStringToArray(String uidString) {

        String[] splitString = uidString.split(" ");

        int length = splitString.length;

        String[] uidArray = new String[length];

        System.arraycopy(splitString, 0, uidArray, 0, length);

        return uidArray;
    }

}
