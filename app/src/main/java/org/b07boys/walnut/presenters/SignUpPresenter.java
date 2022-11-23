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
            view.showToast("Username or password cannot be empty");
        } else {
            auth.signUp(success -> {
                if (success) view.showToast("Successfully registered"); // Move to login fragment, maybe pass the email? Check if we can getCurrentUser here, otherwise change callback to pass user from authmodel
                else view.showToast("Registration failed");
            }, email, password);
        }
    }

    public interface View {
        void showToast(String message);
    }
}
