package org.b07boys.walnut.courses;

public class SessionUtils {
    public static SessionType subsequentSession(SessionType sessionType) {

        int max = getMaxValue();

        return getSessionTypeByID((sessionType.id + 1) % (max + 1));

    }

    private static SessionType getSessionTypeByID(int id) {
        for (SessionType sessionType1 : SessionType.values())
            if (sessionType1.id == id)
                return sessionType1;
        return SessionType.INVALID;
    }

    private static int getMaxValue() {
        int max = -1;
        for (SessionType sessionType : SessionType.values())
            if (sessionType.id > max)
                max = sessionType.id;
        return max;
    }
}
