package org.b07boys.walnut.auth;

import com.google.firebase.auth.FirebaseUser;

public class AccountUtils {
    public static final String adminUID = "qVtJSWUhTsdckl2GAkxkPfzFhHz2";

    public static UserType getUserType(FirebaseUser currentUser) {
        if (currentUser == null) return UserType.NO_USER;
        if (currentUser.getUid().equals(adminUID)) return UserType.ADMIN;
        return UserType.STUDENT;
    }

}
