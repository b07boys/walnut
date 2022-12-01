package org.b07boys.walnut.timeline;

import org.b07boys.walnut.courses.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Timeline {
    private ArrayList<Session> sessions;

    public Timeline() {
        sessions = new ArrayList<>();
    }

    public Timeline(int numSessions){
        sessions = new ArrayList<>(numSessions);
    }

    public Timeline(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public boolean addSession(Session session){
        return sessions.add(session);
    }

    public Session getSession(int sessionNum){
        return sessions.get(sessionNum);
    }

    public boolean removeSession(Session session){
        return sessions.remove(session);
    }
}
