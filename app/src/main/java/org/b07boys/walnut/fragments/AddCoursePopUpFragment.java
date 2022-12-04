package org.b07boys.walnut.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentAddCoursePopUpBinding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AddCoursePopUpFragment extends CoursePopUpFragment {

    private FragmentAddCoursePopUpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddCoursePopUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    protected void setCheckboxes() {

        MaterialButtonToggleGroup buttonToggleGroup = new MaterialButtonToggleGroup(getActivity());
        for (SessionType sessionType : SessionType.values()) {

            if (sessionType != SessionType.INVALID) {
                MaterialButton button = (MaterialButton) getLayoutInflater().inflate(R.layout.button_layout, buttonToggleGroup, false);
                button.setText(sessionType.name());
                buttonToggleGroup.addView(button);
                sessions.put(sessionType, button);
            }

        }
        binding.sessionLayout.addView(buttonToggleGroup);

        for (Course course : CourseCatalogue.getInstance().getCourses()) {

            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(course.getCode());

            prerequisites.put(course.getUID(), checkBox);

            binding.prerequisitesLayout.addView(checkBox);

        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCheckboxes();

        binding.addCourseButton.setOnClickListener(viewButton -> {

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

            if (valid)
                CourseUtils.createCourse(
                        code,
                        name,
                        getCheckedSessions(),
                        getCheckedCourses()
                ).addOnSuccessListener(task -> {
                    sendSnackbar(getActivity().getWindow().getDecorView(), "Success!", Color.parseColor("#007F00"));
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(AddCoursePopUpFragmentDirections.actionAddCoursePopUpFragmentToAdminHomescreenFragment());
                }).addOnFailureListener(er -> {
                    sendSnackbar(getView(), "Error creating course", Color.parseColor("#007F00"));
                });


        });

    }

}