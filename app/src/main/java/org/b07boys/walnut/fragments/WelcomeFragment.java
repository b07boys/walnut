package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.b07boys.walnut.R;
import org.b07boys.walnut.auth.AccountUtils;
import org.b07boys.walnut.auth.UserType;
import org.b07boys.walnut.databinding.FragmentWelcomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment WelcomeFragment.
     */
    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        AccountUtils.getUserType(currentUser, userType -> {
            if (userType == UserType.ADMIN)
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAdminHomescreenFragment());
            else if (userType == UserType.STUDENT)
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(WelcomeFragmentDirections.actionWelcomeFragmentToStudentHomescreenFragment(new String[0]));
        });
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginButton.setOnClickListener(v -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()));
        binding.signupButton.setOnClickListener(v -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment()));
    }
}