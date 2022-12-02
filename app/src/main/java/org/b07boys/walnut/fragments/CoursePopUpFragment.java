package org.b07boys.walnut.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //binding.modifyCourseButton.setOnClickListener(buttonView -> {
          //  CourseUtils.modifyCourse(uid,
            //        code,
              //      name,
                //    sessions,
                  //  prereqs
                    //);

           // CourseUtils.removeCourse(uid);
        //});

        binding.courseCodeField.setText(CoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseCode());
        binding.courseNameField.setText(CoursePopUpFragmentArgs.fromBundle(getArguments()).getCourseTitle());
        setCheckboxes();

        boolean valid = true;

}}
