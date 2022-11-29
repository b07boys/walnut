package org.b07boys.walnut;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import org.b07boys.walnut.auth.AuthStatusCallback;
import org.b07boys.walnut.fragments.LoginFragment;
import org.b07boys.walnut.fragments.SignUpFragment;
import org.b07boys.walnut.models.AuthenticationModel;
import org.b07boys.walnut.presenters.LoginPresenter;
import org.b07boys.walnut.presenters.SignUpPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)

public class SignUpPresenterUnitTests {

    @Mock
    //TODO: LoginPresenter.View loginPresenterView; our actual view (fragment) implements this interface, but the interface doesnt contain all the methods the LoginFragment view can do.
    SignUpFragment signUpView;

    @Mock
    AuthenticationModel authModel;

    @Mock
    FirebaseAuthWeakPasswordException firebaseAuthWeakPasswordException;

    @Mock
    FirebaseException exception;

    @Test
    public void login_empty_email_and_password() {
        SignUpPresenter presenter = new SignUpPresenter(signUpView, authModel);
        presenter.signUp("", "");
        verify(signUpView).showSnackbar("Username or password cannot be empty", "");
    }

    @Test
    public void login_empty_email() {
        SignUpPresenter presenter = new SignUpPresenter(signUpView, authModel);
        presenter.signUp("", "whatever");
        verify(signUpView).showSnackbar("Username or password cannot be empty", "");
    }

    @Test
    public void login_empty_password() {
        SignUpPresenter presenter = new SignUpPresenter(signUpView, authModel);
        presenter.signUp("whatever", "");
        verify(signUpView).showSnackbar("Username or password cannot be empty", "");
    }

    @Test
    public void successful_signup() {
        SignUpPresenter presenter = new SignUpPresenter(signUpView, authModel);
        doAnswer(invocationOnMock -> {
            ((AuthStatusCallback)invocationOnMock.getArguments()[0]).isAuthSuccessful(null);
            return null;
        }).when(authModel).signUp(any(AuthStatusCallback.class), anyString(), anyString());
        presenter.signUp("a@gmail.com", "111111"); //Valid credentials
        verify(signUpView).signUpSuccess();
    }

    @Test
    public void sign_up_weak_password() {
        SignUpPresenter presenter = new SignUpPresenter(signUpView, authModel);
        doAnswer(invocationOnMock -> {
            ((AuthStatusCallback)invocationOnMock.getArguments()[0]).isAuthSuccessful(firebaseAuthWeakPasswordException);
            return null;
        }).when(authModel).signUp(any(AuthStatusCallback.class), anyString(), anyString());
        presenter.signUp("a@gmail.com", "111111"); //Valid credentials
        verify(signUpView).showSnackbar("The password should be at least 6 characters", "");
    }

    @Test
    public void sign_up_failed_other_reason() {
        SignUpPresenter presenter = new SignUpPresenter(signUpView, authModel);
        doAnswer(invocationOnMock -> {
            ((AuthStatusCallback)invocationOnMock.getArguments()[0]).isAuthSuccessful(exception);
            return null;
        }).when(authModel).signUp(any(AuthStatusCallback.class), anyString(), anyString());
        when(exception.getMessage()).thenReturn("");
        presenter.signUp("a@gmail.com", "111111"); //Valid credentials
        verify(signUpView).showSnackbar(anyString(), eq("Try again"));
    }
}