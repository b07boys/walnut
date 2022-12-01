package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.b07boys.walnut.R;
import org.b07boys.walnut.databinding.FragmentSignUpBinding;
import org.b07boys.walnut.models.AuthenticationModel;
import org.b07boys.walnut.presenters.SignUpPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements SignUpPresenter.View {

    private FragmentSignUpBinding binding;

    private SignUpPresenter presenter;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment SignInFragment.
     */
    public static SignUpFragment newInstance(String param1, String param2) {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this, new AuthenticationModel());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        binding.signUpButton.setOnClickListener(view -> presenter.signUp(getEmail(), getPassword()));
        binding.passwordTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPasswordLengthError(binding.passwordTextField);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return binding.getRoot();
    }

    @Override
    public void showSnackbar(String message, String retryMessage) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
                .setAction(retryMessage, view -> presenter.signUp(getEmail(), getPassword()))
                .setAnchorView(binding.signUpButton)
                .show();
    }

    @Override
    public void navigateToLoginScreen() {
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(SignUpFragmentDirections.actionSignInFragmentToLoginFragment().setUserEmail(binding.emailTextField.getText().toString()));
    }

    public void checkPasswordLengthError(View view) {
        if (((TextInputEditText)view).getText().length() <= 5) binding.passwordTextLayout.setError("Password length must be greater than 5");
        else binding.passwordTextLayout.setErrorEnabled(false);
    }

    private String getEmail() {
        return binding.emailTextField.getText().toString();
    }
    private String getPassword() {
        return binding.passwordTextField.getText().toString();
    }

    @Override
    public void signUpSuccess() {
        showSnackbar("Successfully registered", "");
        navigateToLoginScreen();
    }
}