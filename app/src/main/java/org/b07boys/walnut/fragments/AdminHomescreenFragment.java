package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseStructure;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentAdminHomescreenBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomescreenFragment extends Fragment {

    private FragmentAdminHomescreenBinding binding;

    public AdminHomescreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment AdminHomescreenFragment.
     */
    public static AdminHomescreenFragment newInstance(String param1, String param2) {
        AdminHomescreenFragment fragment = new AdminHomescreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.floatingActionButton.setOnClickListener(viewButton -> {
            DialogFragment addPopUp = new AddCoursePopUpFragment();
            addPopUp.show(getActivity().getSupportFragmentManager(), "addPopUp");
        });

    }

}


