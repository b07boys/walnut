package org.b07boys.walnut.presenters;

import org.b07boys.walnut.auth.AuthenticationModel;

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
            authModel.login(email, password);
            view.showLoading();
        }
    }

    public interface View {
        void showLoading();
        void showToast(String message);
    }
}
