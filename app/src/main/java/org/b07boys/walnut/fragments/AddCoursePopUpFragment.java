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

public class AddCoursePopUpFragment extends BottomSheetDialogFragment {

    private FragmentAddCoursePopUpBinding binding;

    private Map<SessionType, MaterialButton> sessions;
    private Map<String, CheckBox> prerequisites;

    public AddCoursePopUpFragment() {
        sessions = new HashMap<>();
        prerequisites = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddCoursePopUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setCheckboxes() {

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

    private SessionType[] getCheckedSessions() {

        Set<SessionType> sessionTypes = new HashSet<>();

        for (Map.Entry<SessionType, MaterialButton> entry : sessions.entrySet()) {
            if (entry.getValue().isChecked())
                sessionTypes.add(entry.getKey());
        }

        return sessionTypes.toArray(new SessionType[0]);

    }

    private String[] getCheckedCourses() {

        Set<String> courses = new HashSet<>();

        for (Map.Entry<String, CheckBox> entry : prerequisites.entrySet()) {
            if (entry.getValue().isChecked())
                courses.add(entry.getKey());
        }

        return courses.toArray(new String[0]);
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
                    getDialog().dismiss();
                }).addOnFailureListener(er -> {
                    sendSnackbar(getView(), "Error creating course", Color.parseColor("#007F00"));
                });


        });

    }

    private void sendSnackbar(View view, String message, int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(color);
        snackbar.show();
    }

}