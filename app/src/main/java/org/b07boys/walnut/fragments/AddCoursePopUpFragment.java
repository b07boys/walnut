package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.DialogFragment;

import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentAddCoursePopUpBinding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddCoursePopUpFragment extends DialogFragment {

    private FragmentAddCoursePopUpBinding binding;

    private Map<SessionType, CheckBox> sessions;
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

    @Override
    public void onResume() {
        super.onResume();

        double scaleX = 0.8;
        double scaleY = 0.8;

        int width = (int) (getResources().getDisplayMetrics().widthPixels * scaleX);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * scaleY);

        getDialog().getWindow().setLayout(width, height);

    }

    private void setCheckboxes() {

        for (SessionType sessionType : SessionType.values()) {

            if (sessionType != SessionType.INVALID) {

                CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setText(sessionType.name());

                sessions.put(sessionType, checkBox);

                binding.sessionLayout.addView(checkBox);

            }

        }

        for (Course course : CourseCatalogue.getInstance().getCourses()) {

            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(course.getCode());

            prerequisites.put(course.getUID(), checkBox);

            binding.prerequisitesLayout.addView(checkBox);

        }

    }

    private SessionType[] getCheckedSessions() {

        Set<SessionType> sessionTypes = new HashSet<>();

        for (Map.Entry<SessionType, CheckBox> entry : sessions.entrySet()) {
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

            if (!valid)
                return;

            CourseUtils.createCourse(
                    code,
                    name,
                    getCheckedSessions(),
                    getCheckedCourses()
            );


        });

    }



}