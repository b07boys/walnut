package org.b07boys.walnut.presenters;

import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.b07boys.walnut.models.AuthenticationModel;

public class SignUpPresenter {
    private final View view;
    private final AuthenticationModel auth;

    public SignUpPresenter(View view, AuthenticationModel auth) {
        this.view = view;
        this.auth = auth;
    }

    public void signUp(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showSnackbar("Username or password cannot be empty", "");
        } else {
            auth.signUp(e -> {
                if (e == null) {
                    view.signUpSuccess();
                } else if (e instanceof FirebaseAuthWeakPasswordException) {
                    view.showSnackbar("The password should be at least 6 characters", "");
                }
                else {
                    view.showSnackbar(e.getMessage(), "Try again");
                }
            }, email, password);
        }
    }

    public interface View {
        void showSnackbar(String message, String retryMessage);
        void navigateToLoginScreen();
        void signUpSuccess();
    }
}
