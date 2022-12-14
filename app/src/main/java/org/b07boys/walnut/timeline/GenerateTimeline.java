package org.b07boys.walnut.timeline;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.Session;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.courses.SessionUtils;
import org.b07boys.walnut.user.DesiredCourses;
import org.b07boys.walnut.user.TakenCourses;
import org.b07boys.walnut.user.User;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
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
        System.out.println("##### PRINTING COURSES DESIRED");
        for(Course course : coursesDesired){
            System.out.println(course);
        }
        System.out.println("##### FINISHED PRINTING COURSES DESIRED");
        System.out.println("##### " + coursesDesired.size() + " COURSES DESIRED");

        //test

        //valid timelines
        ArrayList<Timeline> timelines = new ArrayList<>();

        if(coursesDesired.size() == 0) return timelines;

        //array to generate all possible timelines
        int[] timeline = new int[coursesDesired.size()];

        SessionType currentSession;

        Timeline potentialTimeline = new Timeline(Math.max(3,timeline.length));
        boolean running = true;
        int c = 0;
        while(running){
            if(c % 1000000 == 0) System.out.println("currently on " + c);
            c++;
            // create timelines

            //Timeline potentialTimeline = new Timeline(timeline.length); <-- bad
            currentSession = sessionType;


//            System.out.print("\n##### CONVERTING TIMELINE FOR ");
//            for (int k : timeline) System.out.print(k + " ");
//            System.out.println();

            //for each session
            for(int i = 0; i < Math.max(3,timeline.length); i++){
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

                potentialTimeline = new Timeline(Math.max(3,timeline.length));
            }else{
                potentialTimeline.clearTimeline();
            }

            //increment
            timeline[0]++;
            for (int i = 0; i < timeline.length; i++){
                if (timeline[i] == Math.max(3,timeline.length)){
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

//        System.out.println("##### Printing courses taken");
//        for(Course course : coursesTaken) System.out.println(course);
//        System.out.println("##### FINISHED Printing courses taken");

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

    public static boolean checkPrereqs(){
        System.out.println("$$$$ PRINTING COURSES TAKEN");
        for(Course course : TakenCourses.getInstance().getCourses()) System.out.println(course);
        System.out.println("$$$$ FINISHED PRINTING COURSES TAKEN");

        boolean hasAllPrereq = true;
        for(Course course : DesiredCourses.getInstance().getCourses()){
            System.out.println("Checking " +  course);
            boolean hasPrereqs = false;
            for(String prereq : course.getPrerequisiteUIDS()){
                if(CourseCatalogue.getInstance().getCourseByUID(prereq) == null) hasPrereqs = true;
                if(TakenCourses.getInstance().getCourseByUID(prereq) != null) hasPrereqs = true;
                if(DesiredCourses.getInstance().getCourseByUID(prereq) != null) hasPrereqs = true;
                System.out.println(hasPrereqs);
            }
            if(!hasPrereqs) hasAllPrereq = false;
        }
        return hasAllPrereq;
    }

    public static Timeline generateTimeline2(int maxCoursesPerSem, SessionType sessionType){
        //get courses desired from singleton
        ArrayList<Course> coursesDesired = new ArrayList<>(DesiredCourses.getInstance().getCourses());
        //ArrayList<Course> coursesDesired = getCoursesDesired(coursesTaken);

        Set<Course> coursesTaken = TakenCourses.getInstance().getCourses();

        //test
        System.out.println("##### PRINTING COURSES DESIRED");
        for(Course course : coursesDesired){
            System.out.println(course);
        }
        System.out.println("##### FINISHED PRINTING COURSES DESIRED");
        System.out.println("##### " + coursesDesired.size() + " COURSES DESIRED");

        if(coursesDesired.size() == 0) return new Timeline();

        Timeline timeline = new Timeline();
        Session curSession = new Session(sessionType);
        sessionType = SessionUtils.subsequentSession(sessionType);
        timeline.addSession(curSession);

        Iterator<Course> coursesDesiredIterator = coursesDesired.iterator();

        Course curCourse;

        while(coursesDesiredIterator.hasNext()){
            if(curSession.getCourses().size() >= maxCoursesPerSem){
                coursesTaken.addAll(curSession.getCourses());

                curSession = new Session(sessionType);
                sessionType = SessionUtils.subsequentSession(sessionType);
                timeline.addSession(curSession);
            }

            curCourse = coursesDesiredIterator.next();
            boolean isTakable = true;

            for(String prereq : curCourse.getPrerequisiteUIDS()){
                if (!prereq.equals("") && TakenCourses.getInstance().getCourseByUID(prereq) == null) {
//                      System.out.println("%%% invalid - missing prereq");
                    isTakable = false;
                    break;
                }
            }

            if(isTakable){
                isTakable = false;
                for(SessionType session : curCourse.getOfferingSessions()){
                    if (session == sessionType) {
                        isTakable = true;
                        break;
                    }
                }
            }

            if(isTakable) {
                curSession.addCourse(curCourse);
                coursesDesiredIterator.remove();
                System.out.println("##### ADDING " + curCourse.getName() + " TO TIMELINE");
            }

            if(!coursesDesiredIterator.hasNext() && coursesDesired.size() != 0){
                coursesDesiredIterator = coursesDesired.iterator();

                coursesTaken.addAll(curSession.getCourses());

                curSession = new Session(sessionType);
                sessionType = SessionUtils.subsequentSession(sessionType);
                timeline.addSession(curSession);
            }
        }


        return timeline;
    }
}
