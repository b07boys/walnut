package org.b07boys.walnut;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

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
            authModel.login(email, password);
            view.showLoading();
        }
    }

    public interface View {
        void showLoading();
        void showToast(String message);
    }
}
