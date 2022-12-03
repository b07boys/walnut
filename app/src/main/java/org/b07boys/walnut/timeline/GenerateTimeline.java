package org.b07boys.walnut.timeline;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.Session;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.courses.SessionUtils;
import org.b07boys.walnut.user.DesiredCourses;
import org.b07boys.walnut.user.TakenCourses;
import org.b07boys.walnut.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GenerateTimeline {
    private static int[] lastSessions = {1,1};
    private static List<Course> currentCourses;
    /**
     * Generates every possible valid timeline for a user
     *
     * @param maxCoursesPerSem the maximum number of courses the user wants to take in a single session
     * @param sessionType the next session
     * @return an ArrayList of Timelines that are valid for the user
     */
    public static ArrayList<Timeline> generateTimeline(int maxCoursesPerSem, SessionType sessionType){
        currentCourses = new ArrayList<>();
        //get courses desired from singleton
        ArrayList<Course> coursesDesired = new ArrayList<>(DesiredCourses.getInstance().getCourses());
        //ArrayList<Course> coursesDesired = getCoursesDesired(coursesTaken);


        //test
        System.out.println("PRINTING COURSES DESIRED");
        for(Course course : coursesDesired){
            System.out.println(course);
        }
        System.out.println("PRINTING COURSES DESIRED2");
        System.out.println(coursesDesired.size() + " COURSES DESIRED");

        //test

        //valid timelines
        ArrayList<Timeline> timelines = new ArrayList<>();

        //array to generate all possible timelines
        int[] timeline = new int[coursesDesired.size()];

        for(int i = 0; i < timeline.length; i++){
            timeline[i] = 0;
        }

        SessionType currentSession;

        Timeline potentialTimeline = new Timeline(timeline.length);
        boolean running = true;
        int c = 0;
        while(running){
            if(c % 1000000 == 0) System.out.println(c);
            c++;
            // create timelines

            //Timeline potentialTimeline = new Timeline(timeline.length); <-- bad
            currentSession = sessionType;


//            System.out.print("\n##### CONVERTING TIMELINE FOR ");
//            for(int i = 0; i < timeline.length; i++) System.out.print(timeline[i] + " ");
//            System.out.println();

            //for each session
            for(int i = 0; i < timeline.length; i++){
                potentialTimeline.addSession(new Session(currentSession));
                for(int j = 0; j < timeline.length; j++){
                    if(timeline[j] == i){
                        potentialTimeline.getSession(i).addCourse(coursesDesired.get(j));
//                        System.out.println("##### ADDING " + coursesDesired.get(j).getName());
                    }
                }
                //increment session
                currentSession = SessionUtils.subsequentSession(currentSession);
            }

//            System.out.println("##### CONVERTED TIMELINE: " + formatAsText(potentialTimeline));

            //check if potentialTimeline is valid and add to timelines
            if(checkTimeline(potentialTimeline, maxCoursesPerSem)){
                timelines.add(potentialTimeline);
                System.out.println("##### VALID TIMELINE FOUND");
//                System.out.println(formatAsText(potentialTimeline));

                potentialTimeline = new Timeline(timeline.length);
            }else{
                potentialTimeline.clearTimeline();
            }

            //increment
            timeline[0]++;
            for (int i = 0; i < timeline.length; i++){
                if (timeline[i] == timeline.length){
                    timeline[i] = 0;
                    if(i+1 != timeline.length) timeline[i+1]++;
                    else running = false;
                }
            }

            if(timelines.size() > timeline.length) {
                System.out.println("##### returning early, more than " + timeline.length + " timelines found");
                running = false;
            }
        }

        System.out.println("##### RETURNING " + timelines.size() + " TIMELINES");
        return timelines;
    }


    public static boolean checkTimeline(Timeline timeline, int maxCoursesPerSem){
        Set<Course> coursesTaken = TakenCourses.getInstance().getCourses();
        lastSessions[0] = 1;
        lastSessions[1] = 1;
        //iterate through sessions

        for (Session session : timeline.getSessions()) {
            //check if 3 empty sessions in a row
            if(session.getCourses() == null && lastSessions[0] == 0 && lastSessions[1] == 0){
//                System.out.println("%%% invalid - 3 empty sessions");
                return false;
            }
            lastSessions[1] = lastSessions[0];
            if(session.getCourses() == null){
                lastSessions[0] = 0;
            }else{
                lastSessions[0] = 1;
            }

            //check if courses in session exceeds max
            if(session.getCourses().size() > maxCoursesPerSem){
//                System.out.println("%%% inavlid - too many courses");
                return false;
            }

            currentCourses.clear();
            //iterate through courses
            for (Course course : session.getCourses()) {
                //check session
                boolean isOffered = false;
                for(int i = 0; i < course.getOfferingSessions().length; i++){
                    if(course.getOfferingSessions()[i] == session.getSessionType()){
                        isOffered = true;
                    }
                }
                if(!isOffered){
//                    System.out.println("%%% invalid - course not offered in session");
                    return false;
                }

                //check each prereqs
                String[] prereqs = course.getPrerequisiteUIDS();
                for (String prereq : prereqs) {
                    if (!prereq.equals("") && TakenCourses.getInstance().getCourseByUID(prereq) == null) {
//                      System.out.println("%%% invalid - missing prereq");
                        return false;
                    }
                    currentCourses.add(course);

                }
            }
            coursesTaken.addAll(currentCourses);
        }
        System.out.println("%%% timeline valid");
        return true;
    }

    public static String formatAsText(Timeline timeline){
//        System.out.println("Formatting timeline");
        StringBuilder builder = new StringBuilder();
        for(Session session : timeline.getSessions()){
            builder.append("\n").append(session.getSessionType()).append(": |");
            for(Course course : session.getCourses()){
                builder.append("   ")
                        .append(course.getCode())
                        .append(": ")
                        .append(course.getName())
                        .append("   |");
            }
        }

        return builder.toString();
    }
}
