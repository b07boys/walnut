package org.b07boys.walnut.presenters;

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
            view.showToast("Username or password cannot be empty");
        } else {
            authModel.login(success -> {
                if (success) view.navigateToHomescreen();
                else {
                    view.showToast("Email or password is incorrect");
                    view.clearPasswordInput();
                }
            }, email, password);
        }
    }

    public interface View {
        void showToast(String message);
        void clearPasswordInput();
        void navigateToHomescreen();
    }
}
