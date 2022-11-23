package org.b07boys.walnut.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            authCallback.isAuthSuccessful(true);
                        } else {
                            authCallback.isAuthSuccessful(false);
                        }
                    }
                });
    }
    //TODO: use a callback here because signuppresenter cant tell if the user successfully signed up or not
    public void signUp(AuthStatusCallback authCallback, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            authCallback.isAuthSuccessful(true);
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            authCallback.isAuthSuccessful(false);
                        }
                    }
                });
    }
}
