package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.DialogFragment;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentAddCoursePopUpBinding;

public class AddCoursePopUpFragment extends DialogFragment {

    private FragmentAddCoursePopUpBinding binding;

    public AddCoursePopUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        binding = FragmentAddCoursePopUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (SessionType sessionType : SessionType.values()) {

            if (sessionType != SessionType.INVALID) {

                CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setText(sessionType.name());

                binding.sessionLayout.addView(checkBox);

            }

        }

        for (Course course : CourseCatalogue.getInstance().getCourses()) {

            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(course.getCode());

            binding.sessionLayout.addView(checkBox);

        }


    }

}