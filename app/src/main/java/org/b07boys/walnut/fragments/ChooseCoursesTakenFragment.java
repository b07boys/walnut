package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import org.b07boys.walnut.CourseModel;
import org.b07boys.walnut.CourseRecyclerViewAdapter;
import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.ModifyCourseType;
import org.b07boys.walnut.courses.OnChangeCourseListener;
import org.b07boys.walnut.databinding.FragmentChooseCoursesTakenBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCoursesTakenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCoursesTakenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentChooseCoursesTakenBinding binding;

    public ChooseCoursesTakenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseCoursesTakenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseCoursesTakenFragment newInstance(String param1, String param2) {
        ChooseCoursesTakenFragment fragment = new ChooseCoursesTakenFragment();
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
        //courses = new ArrayList<>(CourseCatalogue.getInstance().getCourses());
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
        binding = FragmentChooseCoursesTakenBinding.inflate(inflater, container, false);
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

//        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.search_bar:
//                        Toast toast = Toast.makeText(getActivity(), "Search functionality not yet implemented", Toast.LENGTH_SHORT);
//                        toast.show();
//                        break;
//                }
//                return true;
//            }
//        });

        binding.extendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList <Course> courses = new ArrayList<>();
                for (CourseModel courseModel : ((CourseRecyclerViewAdapter)binding.rv.getAdapter()).getCourses()) {

                    if (courseModel.getChecked()) {
                        courses.add(courseModel.getCourse());
                        Log.d("fuck", courseModel.getCourse().getUID());
                    }

                }
                CourseUtils.addTakenCourses(courses);
                String[] takenCoursesUID = new String[courses.size()];
                for (int i = 0; i < courses.size(); i++) {
                    takenCoursesUID[i] = courses.get(i).getUID();
                }
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(ChooseCoursesTakenFragmentDirections.actionChooseCoursesTakenFragmentToStudentHomescreenFragment(takenCoursesUID));
            }
        });

        return binding.getRoot();
    }
}