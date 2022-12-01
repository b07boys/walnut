package org.b07boys.walnut.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public void resetPassword(AuthStatusCallback authCallback, String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> authCallback.isAuthSuccessful(task.getException()));
    }
}
