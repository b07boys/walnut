package org.b07boys.walnut;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.b07boys.walnut.databinding.FragmentLoginBinding;

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

    private Button loginButton;
    private TextView registerText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout passwordTextLayout;

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
        loginButton = binding.loginButton;
        registerText = binding.registerText;
        emailEditText = binding.emailTextField;
        passwordEditText = binding.passwordTextField;
        passwordTextLayout = binding.passwordTextLayout;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.homescreenFragment);
                    //TODO: remove loginFragment from back stack
                } else {
                    //TODO: Use a callback to directly get the state of the login call to know when or not to show this, login can fail by incorrect auth or empty fields which we want to note specifically
                    showToast("Email or password is incorrect");
                    passwordEditText.setText("");
                }

            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.signInFragment);
                //Toast.makeText(getActivity(), "registered", Toast.LENGTH_LONG).show();
            }
        });
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //TODO: figure out how to call on text changed
            @Override
            public void onFocusChange(View view, boolean b) {
                checkPasswordLengthError(view);
            }
        });
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showLoading() {
        //TODO: do something
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void checkPasswordLengthError(View view) {
        if (((TextInputEditText)view).getText().length() <= 5) passwordTextLayout.setError("Password length must be greater than 5");
        else passwordTextLayout.setErrorEnabled(false);
    }
}