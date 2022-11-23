package org.b07boys.walnut.presenters;

import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.b07boys.walnut.models.AuthenticationModel;

public class LoginPresenter {
    private final View view;
    private final AuthenticationModel authModel;

    public LoginPresenter(View view, AuthenticationModel authModel) {
        this.view = view;
        this.authModel = authModel;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showSnackbar("Username or password cannot be empty", "");
        } else {
            authModel.login(e -> {
                if (e == null) view.navigateToHomescreen();
                else if (e instanceof FirebaseAuthInvalidUserException) {
                    view.showSnackbar("The email or password does not exist", "");
                }
                else {
                    view.showSnackbar(e.getMessage(), "Try again");
                    view.clearPasswordInput();
                }
            }, email, password);
        }
    }

    public interface View {
        void showSnackbar(String message, String retryMessage);
        void clearPasswordInput();
        void navigateToHomescreen();
    }
}
