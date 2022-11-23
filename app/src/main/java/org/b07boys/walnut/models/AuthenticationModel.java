package org.b07boys.walnut.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.b07boys.walnut.auth.AuthStatusCallback;

public class AuthenticationModel {

    private FirebaseAuth mAuth;

    public AuthenticationModel() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void login(AuthStatusCallback authCallback, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        authCallback.isAuthSuccessful(task.getException());
                    } else {
                        authCallback.isAuthSuccessful(task.getException());
                    }
                });
    }
    public void signUp(AuthStatusCallback authCallback, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        authCallback.isAuthSuccessful(task.getException());
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        authCallback.isAuthSuccessful(task.getException()); // Throwing an exception here isn't recognized by calling method
                    }
                });
    }
}
