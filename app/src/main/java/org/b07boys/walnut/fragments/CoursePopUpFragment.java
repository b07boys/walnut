package org.b07boys.walnut.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.database.adapters.CourseListAdapter;
import org.b07boys.walnut.databinding.FragmentCoursePopUpBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursePopUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursePopUpFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCoursePopUpBinding binding;
    private Map<SessionType, MaterialButton> sessions;
    private Map<String, CheckBox> prerequisites;

    public CoursePopUpFragment() {
        sessions = new HashMap<>();
        prerequisites = new HashMap<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoursePopUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursePopUpFragment newInstance(String param1, String param2) {
        CoursePopUpFragment fragment = new CoursePopUpFragment();
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
    }


    private void setCheckboxes() {

        MaterialButtonToggleGroup buttonToggleGroup = new MaterialButtonToggleGroup(getActivity());

        String[] s = CoursePopUpFragmentArgs.fromBundle(getArguments()).getSessions();
        for (SessionType sessionType : SessionType.values()) {

            if (sessionType != SessionType.INVALID) {
                MaterialButton button = (MaterialButton) getLayoutInflater().inflate(R.layout.button_layout, buttonToggleGroup, false);
                button.setText(sessionType.name());
                buttonToggleGroup.addView(button);
                sessions.put(sessionType, button);
                for(int i = 0; i<s.length; i++){
                    if (sessionType.name().equals(s[i])){
                        button.setChecked(true);
                    }
                }

            }

        }
        binding.sessionLayout.addView(buttonToggleGroup);
        String[] p = CoursePopUpFragmentArgs.fromBundle(getArguments()).getPrerequisites();
        for (Course course : CourseCatalogue.getInstance().getCourses()) {

            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(course.getCode());

            prerequisites.put(course.getUID(), checkBox);
            binding.prerequisitesLayout.addView(checkBox);

            for(int i = 0; i<p.length; i++){
                Log.d("lll", course.getUID());
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        int s2Length = getCheckedSessions().length;
        SessionType[] s2 = new SessionType[s2Length];

        for (int i = 0; i < s2Length; i++){
            s2[i] = getCheckedSessions()[i];
        }

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
            CourseUtils.modifyCourse(CoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseUID(),
                    binding.courseCodeField.getText().toString(),
                    binding.courseNameField.getText().toString(),
                    getCheckedSessions(),
                    getCheckedCourses());
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(CoursePopUpFragmentDirections.actionCoursePopUpFragmentToAdminHomescreenFragment());

        }});

        binding.deleteCourseButton.setOnClickListener(buttonView -> {
            CourseUtils.removeCourse(CoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseUID());
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(CoursePopUpFragmentDirections.actionCoursePopUpFragmentToAdminHomescreenFragment());
        });

        binding.courseCodeField.setText(CoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseCode());
        binding.courseNameField.setText(CoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseTitle());
        setCheckboxes();

        boolean valid = true;

}}
