package org.b07boys.walnut.auth;

import com.google.firebase.auth.FirebaseUser;

import org.b07boys.walnut.database.DatabaseNode;
import org.b07boys.walnut.database.PromiseReceivedData;

public class AccountUtils {

    public static void getUserType(FirebaseUser currentUser, PromiseReceivedData<UserType> promise) {

        if (currentUser == null) {
            promise.onReceive(UserType.NO_USER);
            return;
        }

        new DatabaseNode("admins").nodeSnapshot("").addOnCompleteListener(task -> {
            if (task.getResult().hasChild(currentUser.getUid()))
                promise.onReceive(UserType.ADMIN);
            else
                promise.onReceive(UserType.STUDENT);
        });

    }

}
