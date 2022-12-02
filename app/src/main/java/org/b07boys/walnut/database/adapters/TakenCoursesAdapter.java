package org.b07boys.walnut.database.adapters;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.database.DatabaseAdapter;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TakenCoursesAdapter implements DatabaseAdapter {

    public String courses;

    public TakenCoursesAdapter() {}

    public TakenCoursesAdapter(String[] courses) {

        StringBuilder builder = new StringBuilder();
        for (String course : courses) {
            builder.append(course).append(" ");
        }
        this.courses = builder.toString().trim();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("courses", courses);
        return result;
    }

    @Exclude
    public String[] getCourse() {
        return courses.split(" ");
    }
}
