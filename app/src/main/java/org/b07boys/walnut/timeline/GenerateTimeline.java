package org.b07boys.walnut.timeline;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.Session;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.courses.SessionUtils;
import org.b07boys.walnut.user.TakenCourses;
import org.b07boys.walnut.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GenerateTimeline {

    /**
     * Generates every possible valid timeline for a user
     *
     * @param user object the student who's timeline to generate
     * @param maxCoursesPerSem the maximum number of courses the user wants to take in a single session
     * @param sessionType the next session
     * @return an ArrayList of Timelines that are valid for the user
     */
    public static ArrayList<Timeline> generateTimeline(User user, int maxCoursesPerSem, SessionType sessionType){
        Set<Course> coursesTaken = user.getTakenCourses().getCourses();
        ArrayList<Course> coursesDesired = getCoursesDesired(coursesTaken);
        ArrayList<Timeline> timelines = new ArrayList<>();

        assert coursesDesired != null;
        int[] timeline = new int[coursesDesired.size()];

        for(int i = 0; i < timeline.length; i++){
            timeline[i]++;
        }

        boolean running = true;
        while(running){
            // create timelines
            Timeline potentialTimeline = new Timeline(timeline.length);
            SessionType currentSession = sessionType;

            //for each session
            for(int i = 0; i < potentialTimeline.getSessions().size(); i++){
                potentialTimeline.addSession(new Session(currentSession));
                for(int j = 0; j < timeline.length; j++){
                    if(timeline[j] == i){
                        potentialTimeline.getSession(i).addCourse(coursesDesired.get(j));
                    }
                }
                currentSession = SessionUtils.subsequentSession(currentSession);
            }

            //check if potentialTimeline is vaild and add to timelines
            if(checkTimeline(potentialTimeline, maxCoursesPerSem)) timelines.add(potentialTimeline);

            timeline[0]++;
            for (int i = 0; i < timeline.length; i++){
                if (timeline[i] == timeline.length){
                    timeline[i] = 0;
                    if(i+1 != timeline.length) timeline[i+1]++;
                    else running = false;
                }
            }
        }

        return timelines;
    }

    public static ArrayList<Course> getCoursesDesired(Set<Course> coursesTaken){

        //TODO
        return null;
    }

    public static boolean checkTimeline(Timeline timeline, int maxCoursesPerSem){
        Set<Course> coursesTaken = TakenCourses.getInstance().getCourses();

        //iterate through sessions
        for (Session session : timeline.getSessions()) {
            List<Course> currentCourses = new ArrayList<>();
            //iterate through courses
            for (Course course : session.getCourses()) {
                //check session
                boolean isOffered = false;
                for(int i = 0; i < course.getOfferingSessions().length; i++){
                    if(course.getOfferingSessions()[i] == session.getSessionType()){
                        isOffered = true;
                    }
                }
                if(!isOffered) return false;

                //check each prereqs
                String[] prereqs = course.getPrerequisiteUIDS();
                for (String prereq : prereqs) {
                    if (TakenCourses.getInstance().getCourseByUID(prereq) == null) {
                        return false;
                    }
                    currentCourses.add(TakenCourses.getInstance().getCourseByUID(prereq));
                }
            }
            coursesTaken.addAll(currentCourses);
        }
        return true;
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
