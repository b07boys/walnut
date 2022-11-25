package org.b07boys.walnut.courses;

import java.util.ArrayList;


public class GenerateTimeline {

    /**
     *
     * @param timeline
     * @param desiredCourses
     * @param takenCourses
     * @param maxCoursesPerSem
     * @return
     */
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
