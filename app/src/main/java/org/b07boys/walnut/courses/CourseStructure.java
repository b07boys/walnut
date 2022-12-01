package org.b07boys.walnut.courses;

import java.util.HashSet;
import java.util.Set;

public class CourseStructure {

    private final Set<Course> courses;
    private Set<OnChangeCourseListener> listeners;

    public CourseStructure(Set<Course> courses){
        this.courses = courses;
        listeners = new HashSet<>();
    }

    public void addCourse(Course course){
        courses.add(course);
        notifyAdd(course);
    }

    public void removeCourse(Course course){
        courses.remove(course);
        notifyRemove(course);
    }

    public void removeCourseByUID(String uid) {
        removeCourse(getCourseByUID(uid));
    }

    public Set<Course> getCourses(){
        return courses;
    }

    public void registerListener(OnChangeCourseListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(OnChangeCourseListener listener) {
        listeners.remove(listener);
    }

    public void modifyCourse(Course course) {
        notifyModify(course);
    }

    private void notifyAdd(Course course) {
        for (OnChangeCourseListener listener : listeners)
            listener.onAdd(course);
    }

    private void notifyRemove(Course course) {
        for (OnChangeCourseListener listener : listeners)
            listener.onRemove(course);
    }

    private void notifyModify(Course course) {
        for (OnChangeCourseListener listener : listeners)
            listener.onModify(course);
    }


    public Course getCourseByUID(String uid) {
        for (Course course : courses) {
            if (course.getUID().equals(uid))
                return course;
        }
        return null;
    }

    /**
     * Checks whether a course is already present in the catalog.
     *
     * @return true if course already exits, false otherwise.
     */
    public boolean courseExists(Course target){
        for (Course course : courses) {
            if (course.getUID().equals(target.getUID()))
                return true;
        }
        return false;
    }

}
