package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

import org.b07boys.walnut.CourseRecyclerViewAdapter;
import org.b07boys.walnut.CourseModel;
import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.ModifyCourseType;
import org.b07boys.walnut.courses.OnChangeCourseListener;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentChooseCoursesDesiredBinding;
import org.b07boys.walnut.user.DesiredCourses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCoursesDesiredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCoursesDesiredFragment extends Fragment {

    FragmentChooseCoursesDesiredBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Course> courses;

    public ChooseCoursesDesiredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseCoursesDesiredFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseCoursesDesiredFragment newInstance(String param1, String param2) {
        ChooseCoursesDesiredFragment fragment = new ChooseCoursesDesiredFragment();
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
        courses = new ArrayList<>(CourseCatalogue.getInstance().getCourses());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CourseCatalogue.getInstance().registerListener(new OnChangeCourseListener() {
            @Override
            public void onModify(Course course, ModifyCourseType modifyType) {
                binding.rv.setAdapter(new CourseRecyclerViewAdapter(CourseCatalogue.getInstance().getCourses()));
            }
        });


        // Inflate the layout for this fragment
        binding = FragmentChooseCoursesDesiredBinding.inflate(inflater, container, false);
        binding.rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // Initialize all courses into the list
        ArrayList<CourseModel> courseModels = new ArrayList<>();
        for (Course course : CourseCatalogue.getInstance().getCourses()) {
            CourseModel temp = new CourseModel();
            temp.setCourse(course);
            courseModels.add(temp);
        }
        binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
        binding.rv.setNestedScrollingEnabled(false);

        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search_bar:
                        Toast toast = Toast.makeText(getActivity(), "Search functionality not yet implemented", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                }
                return true;
            }
        });

        binding.toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId == R.id.button1) {
                    if (isChecked) filter("FALL");
                    else binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
                } else if (checkedId == R.id.button2) {
                    if (isChecked) filter("WINTER");
                    else binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
                } else if (checkedId == R.id.button3) {
                    if (isChecked) filter("SUMMER");
                    else binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
                }
            }
        });

        binding.extendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (CourseModel courseModel : ((CourseRecyclerViewAdapter)binding.rv.getAdapter()).getCourses()) {
                    if (courseModel.getChecked()) DesiredCourses.getInstance().addCourse(courseModel.getCourse());
                }
            }
        });

        return binding.getRoot();
    }

    private void filter(String text) {
        // Store list of unfiltered current courses in the recyclerview and their state (checked or not)
        ArrayList<CourseModel> courseModels = ((CourseRecyclerViewAdapter) binding.rv.getAdapter()).getCourses();
        if (text == null) {
            // Return to previous state
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
        }
        ArrayList<CourseModel> filteredCourseModelList = new ArrayList<>();
        // Filter on session
        for (CourseModel courseModel : courseModels) {
            if (Arrays.asList(courseModel.getCourse().getOfferingSessions()).contains(SessionType.valueOf(text)))
            {
                filteredCourseModelList.add(courseModel);
                ArrayList<CourseModel> currentItems = ((CourseRecyclerViewAdapter) binding.rv.getAdapter()).getCourses();
                currentItems.indexOf(courseModel);
            }
        }
        if (!filteredCourseModelList.isEmpty()) {
            // Pass filtered list to adapter
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(filteredCourseModelList));
        } else {
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(new ArrayList<CourseModel>()));
        }
    }

}