package org.b07boys.walnut.timeline;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.user.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class GenerateTimeline {

    public static Timeline generateTimeline(User user, int maxCoursesPerSem){
        Set<Course> coursesTaken = user.getTakenCourses().getCourses();
        HashSet<Course> coursesDesired = getCoursesDesired(coursesTaken);
        ArrayList<Timeline> timelines = new ArrayList<>();


        int[] timeline = new int[coursesDesired.size()];

        for(int i = 0; i < timeline.length; i++){
            timeline[i]++;
        }

        while(true){
            timeline[0]++;
            for (int i = 0; i < timeline.length; i++){
                if (timeline[i] == timeline.length){
                    timeline[i] = 0;
                    timeline[i+1]++;
                }
            }

            break;
        }



        return null;
    }

    public static HashSet<Course> getCoursesDesired(Set<Course> coursesTaken){
        //TODO
        return null;
    }


    public static ArrayList<ArrayList<ArrayList<Course>>> generate(
            ArrayList<ArrayList<Course>> timeline, ArrayList<Course> desiredCourses,
            ArrayList<Course> takenCourses, int maxCoursesPerSem){

        ArrayList<ArrayList<ArrayList<Course>>> timelinePossibilities = new ArrayList<>();
        gen(timeline,desiredCourses,takenCourses,maxCoursesPerSem);

        return timelinePossibilities;
    }

    private static void gen(
            ArrayList<ArrayList<Course>> timeline, ArrayList<Course> desiredCourses,
            ArrayList<Course> takenCourses, int maxCoursesPerSem){

    }
}
