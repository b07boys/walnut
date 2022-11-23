package org.b07boys.walnut.presenters;

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
            view.showSnackbar("Username or password cannot be empty");
        } else {
            auth.signUp(success -> {
                if (success) {
                    view.showSnackbar("Successfully registered"); // Move to login fragment, maybe pass the email? Check if we can getCurrentUser here, otherwise change callback to pass user from authmodel
                    view.navigateToLoginScreen();
                }
                else view.showSnackbar("Registration failed");
            }, email, password);
        }
    }

    public interface View {
        void showSnackbar(String message);
        void navigateToLoginScreen();
    }
}
