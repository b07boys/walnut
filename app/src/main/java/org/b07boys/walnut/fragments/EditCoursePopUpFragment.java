package org.b07boys.walnut.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentCoursePopUpBinding;

import java.util.HashMap;
import java.util.Map;

public class EditCoursePopUpFragment extends CoursePopUpFragment {

    private FragmentCoursePopUpBinding binding;

    protected void setCheckboxes() {

        MaterialButtonToggleGroup buttonToggleGroup = new MaterialButtonToggleGroup(getActivity());

        String[] s = EditCoursePopUpFragmentArgs.fromBundle(getArguments()).getSessions();
        for (SessionType sessionType : SessionType.values()) {

            if (sessionType != SessionType.INVALID) {
                MaterialButton button = (MaterialButton) getLayoutInflater().inflate(R.layout.button_layout, buttonToggleGroup, false);
                button.setText(sessionType.name());
                buttonToggleGroup.addView(button);
                sessions.put(sessionType, button);
                for (int i = 0; i < s.length; i++){
                    if (sessionType.name().equals(s[i])){
                        button.setChecked(true);
                    }
                }

            }

        }
        binding.sessionLayout.addView(buttonToggleGroup);
        String[] p = EditCoursePopUpFragmentArgs.fromBundle(getArguments()).getPrerequisites();
        for (Course course : CourseCatalogue.getInstance().getCourses()) {

            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(course.getCode());

            prerequisites.put(course.getUID(), checkBox);
            binding.prerequisitesLayout.addView(checkBox);

            for (int i = 0; i < p.length; i++){
                if (course.getUID().equals(p[i])){
                    checkBox.setChecked(true);
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCoursePopUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCheckboxes();

        binding.modifyCourseButton.setOnClickListener(buttonView -> {
            String code = binding.courseCodeField.getText().toString();
            String name = binding.courseNameField.getText().toString();

            boolean valid = true;

            if (code.trim().equals("")) {
                binding.courseCodeLayout.setErrorEnabled(true);
                binding.courseCodeLayout.setError("Course code must not be empty");
                valid = false;
            } else {
                binding.courseCodeLayout.setErrorEnabled(false);
            }

            if (name.trim().equals("")) {
                binding.courseNameLayout.setErrorEnabled(true);
                binding.courseNameLayout.setError("Course name must not be empty");
                valid = false;
            } else {
                binding.courseNameLayout.setErrorEnabled(false);
            }
            if(valid){
            CourseUtils.modifyCourse(EditCoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseUID(),
                    binding.courseCodeField.getText().toString(),
                    binding.courseNameField.getText().toString(),
                    getCheckedSessions(),
                    getCheckedCourses()).addOnSuccessListener(task -> {
                        sendSnackbar(getActivity().getWindow().getDecorView(), "Success!", Color.parseColor("#007F00"));
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(EditCoursePopUpFragmentDirections.actionCoursePopUpFragmentToAdminHomescreenFragment());
                    }).addOnFailureListener(er -> {
                        sendSnackbar(getView(), "Error modifying course", Color.parseColor("#007F00"));
                    });

        }});

        binding.deleteCourseButton.setOnClickListener(buttonView -> {
            CourseUtils.removeCourse(EditCoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseUID())
                    .addOnSuccessListener(task -> {
                        sendSnackbar(getActivity().getWindow().getDecorView(), "Success!", Color.parseColor("#007F00"));
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(EditCoursePopUpFragmentDirections.actionCoursePopUpFragmentToAdminHomescreenFragment());
                    }).addOnFailureListener(er -> {
                        sendSnackbar(getView(), "Error removing course", Color.parseColor("#007F00"));
                    });
        });

        binding.courseCodeField.setText(EditCoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseCode());
        binding.courseNameField.setText(EditCoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseTitle());

}}
