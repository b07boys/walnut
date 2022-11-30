package org.b07boys.walnut.courses;

import java.util.LinkedHashSet;

public class Timeline {
    private LinkedHashSet<Session> sessions;

    public Timeline(LinkedHashSet<Session> session) {
        this.sessions = session;
    }

    public LinkedHashSet<Session> getSessions() {
        return sessions;
    }

    public void setSessions(LinkedHashSet<Session> sessions) {
        this.sessions = sessions;
    }

    public boolean addSession(Session session){
        return sessions.add(session);
    }

    public boolean removeSession(Session session){
        return sessions.remove(session);
    }
}
