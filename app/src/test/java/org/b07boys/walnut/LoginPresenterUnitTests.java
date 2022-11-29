package org.b07boys.walnut;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import org.b07boys.walnut.auth.AuthStatusCallback;
import org.b07boys.walnut.fragments.LoginFragment;
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

public class LoginPresenterUnitTests {

    @Mock
    //LoginPresenter.View loginPresenterView; our actual view (fragment) implements this interface, but the interface doesnt contain all the methods the LoginFragment view can do. maybe it should.
    LoginFragment loginView;

    @Mock
    SignUpPresenter.View signUpPresenterView;

    @Mock
    AuthenticationModel authModel;

    @Mock
    FirebaseAuthInvalidUserException firebaseAuthInvalidUserException;

    @Mock
    FirebaseException e;

    @Test
    public void login_empty_email_and_password() {
        LoginPresenter presenter = new LoginPresenter(loginView, authModel);
        presenter.login("", "");
        verify(loginView).showSnackbar("Username or password cannot be empty", "");
    }

    @Test
    public void login_empty_email() {
        LoginPresenter presenter = new LoginPresenter(loginView, authModel);
        presenter.login("", "whatever");
        verify(loginView).showSnackbar("Username or password cannot be empty", "");
    }

    @Test
    public void login_empty_password() {
        LoginPresenter presenter = new LoginPresenter(loginView, authModel);
        presenter.login("whatever", "");
        verify(loginView).showSnackbar("Username or password cannot be empty", "");
    }

    @Captor
    private ArgumentCaptor<AuthStatusCallback> authStatusCallbackArgumentCaptor;

    @Test
    public void successful_login() {
        LoginPresenter presenter = new LoginPresenter(loginView, authModel);
        doAnswer(invocationOnMock -> {
            ((AuthStatusCallback)invocationOnMock.getArguments()[0]).isAuthSuccessful(null);
            return null;
        }).when(authModel).login(any(AuthStatusCallback.class), anyString(), anyString());
        presenter.login("a@gmail.com", "111111"); //Valid credentials
        verify(loginView).navigateToHomescreen();
    }

    @Test
    public void login_wrong_email_or_password() {
        LoginPresenter presenter = new LoginPresenter(loginView, authModel);
        doAnswer(invocationOnMock -> {
            ((AuthStatusCallback)invocationOnMock.getArguments()[0]).isAuthSuccessful(firebaseAuthInvalidUserException);
            return null;
        }).when(authModel).login(any(AuthStatusCallback.class), anyString(), anyString());
        presenter.login("a@gmail.com", "111111"); //Valid credentials
        verify(loginView).showSnackbar("The email or password does not exist", "");
    }

    @Test
    public void login_failed_other_reason() {
        LoginPresenter presenter = new LoginPresenter(loginView, authModel);
        doAnswer(invocationOnMock -> {
            ((AuthStatusCallback)invocationOnMock.getArguments()[0]).isAuthSuccessful(e);
            return null;
        }).when(authModel).login(any(AuthStatusCallback.class), anyString(), anyString());
        when(e.getMessage()).thenReturn("");
        presenter.login("a@gmail.com", "111111"); //Valid credentials
        verify(loginView).showSnackbar(anyString(), eq("Try again"));
        verify(loginView).clearPasswordInput();
    }
}