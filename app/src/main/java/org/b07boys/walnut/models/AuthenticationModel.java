package org.b07boys.walnut.models;

import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.auth.AuthStatusCallback;

public class AuthenticationModel {

    private FirebaseAuth mAuth;

    public AuthenticationModel() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void login(AuthStatusCallback authCallback, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> authCallback.isAuthSuccessful(task.getException()));
    }
    public void signUp(AuthStatusCallback authCallback, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> authCallback.isAuthSuccessful(task.getException()));
    }
}
