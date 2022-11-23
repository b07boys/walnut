package org.b07boys.walnut.presenters;

import org.b07boys.walnut.auth.AuthenticationModel;

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
            auth.signUp(email, password);
            //view.showToast("Successfully registered");
        }
    }

    public interface View {
        void showToast(String message);
    }
}
