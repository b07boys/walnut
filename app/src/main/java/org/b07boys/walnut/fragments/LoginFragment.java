package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.b07boys.walnut.R;
import org.b07boys.walnut.presenters.LoginPresenter;
import org.b07boys.walnut.databinding.FragmentLoginBinding;
import org.b07boys.walnut.models.AuthenticationModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginPresenter.View {

    private FragmentLoginBinding binding;
    private LoginPresenter presenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (UID.equals("qVtJSWUhTsdckl2GAkxkPfzFhHz2")) Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToAdminHomescreenFragment());
        else Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(LoginFragmentDirections.actionLoginFragmentToStudentHomescreenFragment());
    }

    public String getEmail() {
        return binding.emailTextField.getText().toString();
    }
    public String getPassword() {
        return binding.passwordTextField.getText().toString();
    }
}