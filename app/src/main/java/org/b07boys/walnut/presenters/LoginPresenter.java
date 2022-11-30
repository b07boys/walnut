package org.b07boys.walnut.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import org.b07boys.walnut.database.DatabaseNode;
import org.b07boys.walnut.auth.AuthStatusCallback;
import org.b07boys.walnut.models.AuthenticationModel;

public class LoginPresenter {
    private final View view;
    private final AuthenticationModel authModel;

    public LoginPresenter(View view, AuthenticationModel authModel) {
        this.view = view;
        this.authModel = authModel;
    }

    public void login(String email, String password) {
        // TODO: Are we supposed to use view.getEmail here so that we can stub it in the Mockito? nah
        if (email.isEmpty() || password.isEmpty()) {
            view.showSnackbar("Username or password cannot be empty", "");
        } else {
            authModel.login(e -> {
                if (e == null) {

                    view.navigateToHomescreen();

                }
                else if (e instanceof FirebaseAuthInvalidUserException) {
                    view.showSnackbar("The email or password does not exist", "");
                } else {
                    view.showSnackbar(e.getMessage(), "Try again");
                    view.clearPasswordInput();
                }
            }, email, password);
        }
    }

    public interface View { //TODO: add getemail, etc all other methods LoginFragment implements so that we can mock LoginPresenter.View and not LoginFragment idk if necessary but i wanna
        // plus better if we can get every method into the interface. same for signuppresenter bc it has a different interface
        void showSnackbar(String message, String retryMessage);
        void clearPasswordInput();
        void navigateToHomescreen();
    }
}
