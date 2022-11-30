package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.b07boys.walnut.R;
import org.b07boys.walnut.auth.AccountUtils;
import org.b07boys.walnut.auth.UserType;
import org.b07boys.walnut.databinding.FragmentLoginBinding;
import org.b07boys.walnut.models.AuthenticationModel;
import org.b07boys.walnut.presenters.LoginPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginPresenter.View {

    private FragmentLoginBinding binding;
    private LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this, new AuthenticationModel());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.emailTextField.setText(LoginFragmentArgs.fromBundle(getArguments()).getUserEmail());
        binding.loginButton.setOnClickListener(v -> presenter.login(getEmail(), getPassword()));
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
        binding.forgotPasswordText.setOnClickListener(view1 -> presenter.resetPassword(getEmail()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showSnackbar(String message, String retryMessage) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.loginButton)
                .setAction(retryMessage, view -> presenter.login(getEmail(), getPassword()))
                .show();
    }

    @Override
    public void clearPasswordInput() {
        binding.passwordTextField.setText("");
    }

    public void checkPasswordLengthError(View view) {
        if (((TextInputEditText)view).getText().length() <= 5) binding.passwordTextLayout.setError("Password length must be greater than 5");
        else binding.passwordTextLayout.setErrorEnabled(false);
    }

    @Override
    public void navigateToHomescreen() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        AccountUtils.getUserType(currentUser, userType -> {
            if (userType == UserType.ADMIN)
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToAdminHomescreenFragment());
            else if (userType == UserType.STUDENT)
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToStudentHomescreenFragment());
        });
    }

    public String getEmail() {
        return binding.emailTextField.getText().toString();
    }
    public String getPassword() {
        return binding.passwordTextField.getText().toString();
    }
}