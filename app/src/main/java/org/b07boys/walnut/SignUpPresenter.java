package org.b07boys.walnut;

public class SignUpPresenter {
    private View view;
    private AuthenticationModel auth;

    public SignUpPresenter(View view, AuthenticationModel auth) {
        this.view = view;
        this.auth = auth;
    }

    public void signUp(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showToast("Username or password cannot be empty");
        } else {
            auth.signUp(new AuthStatusCallback() {
                @Override
                public void isAuthSuccessful(boolean success) {
                    if (success) view.showToast("Successfully registered"); // Move to login fragment, maybe pass the email? Check if we can getCurrentUser here, otherwise change callback to pass user from authmodel
                    else view.showToast("Registration failed");
                }
            }, email, password);
        }
    }

    public interface View {
        void showToast(String message);
    }
}
