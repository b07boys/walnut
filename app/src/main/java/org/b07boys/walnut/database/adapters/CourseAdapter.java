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

    private String code;
    private String name;
    private Map<String, String> offeringSessions;
    private Map<String, String> prerequisites;

    public CourseAdapter() {
        // this constructor is required for firebase parsing
    }

    public CourseAdapter(String code, String name, SessionType[] offeringSessions, Course[] prerequisites) {

        this.code = code;
        this.name = name;

        this.offeringSessions = new HashMap<>();
        this.prerequisites = new HashMap<>();

        for (SessionType sessionType : offeringSessions)
            this.offeringSessions.put(sessionType.name(), "");

        for (Course course : prerequisites)
            this.prerequisites.put(course.getUID(), "");

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
        course.setOfferingSessions(convertSessionTypesToArray());
        course.setPrerequisiteUIDS(convertPrerequisitesToArray());
    }

    @Exclude
    public Course createCourse(String uid) {
        return new Course(
                uid,
                code,
                name,
                convertSessionTypesToArray(),
                convertPrerequisitesToArray()
        );
    }

    @Exclude
    private SessionType[] convertSessionTypesToArray() {

        SessionType[] sessionTypes = new SessionType[offeringSessions.size()];

        int i = 0;
        for (String key : offeringSessions.keySet()) {
            sessionTypes[i] = SessionType.valueOf(key);
            i++;
        }

        return sessionTypes;
    }

    @Exclude
    private String[] convertPrerequisitesToArray() {
        return prerequisites.keySet().toArray(new String[0]);
    }

}
