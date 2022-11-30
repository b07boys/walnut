package org.b07boys.walnut.auth;

import com.google.firebase.auth.FirebaseUser;

import org.b07boys.walnut.database.DatabaseNodeEditor;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.PromiseReceivedData;

public class AccountUtils {

    public static void getUserType(FirebaseUser currentUser, PromiseReceivedData<UserType> promise) {

        if (currentUser == null) {
            promise.onReceive(UserType.NO_USER);
            return;
        }

        new DatabaseNodeEditor(DatabasePaths.ADMINS.path).nodeSnapshot("").addOnCompleteListener(task -> {
            if (task.getResult().hasChild(currentUser.getUid()))
                promise.onReceive(UserType.ADMIN);
            else
                promise.onReceive(UserType.STUDENT);
        });

    }

}
