package org.b07boys.walnut;

public class LoginPresenter {
    private View view;
    private AuthenticationModel authModel;

    public LoginPresenter(View view, AuthenticationModel authModel) {
        this.view = view;
        this.authModel = authModel;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showToast("Username or password cannot be empty");
        } else {
            authModel.login(new AuthStatusCallback() {
                @Override
                public void isAuthSuccessful(boolean success) {
                    if (success) view.goToHomescreen();
                    else {
                        view.showToast("Email or password is incorrect");
                        view.clearPasswordInput();
                    }
                }
            }, email, password);
        }
    }

    public interface View {
        void showToast(String message);
        void clearPasswordInput();
        void goToHomescreen();
    }
}
