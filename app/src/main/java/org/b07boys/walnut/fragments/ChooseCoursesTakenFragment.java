package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import org.b07boys.walnut.lists.CourseModel;
import org.b07boys.walnut.lists.CourseRecyclerViewAdapter;
import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.ModifyCourseType;
import org.b07boys.walnut.courses.OnChangeCourseListener;
import org.b07boys.walnut.databinding.FragmentChooseCoursesTakenBinding;
import org.b07boys.walnut.main.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCoursesTakenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCoursesTakenFragment extends Fragment {
    FragmentChooseCoursesTakenBinding binding;

    public ChooseCoursesTakenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseCoursesTakenFragment.
     */
    public static ChooseCoursesTakenFragment newInstance(String param1, String param2) {
        ChooseCoursesTakenFragment fragment = new ChooseCoursesTakenFragment();
        return fragment;
    }

    private ArrayList<CourseModel> ogCourseModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ogCourseModels = new ArrayList<>();
        for (Course c : CourseCatalogue.getInstance().getCourses()) {
            CourseModel temp = new CourseModel();
            temp.setCourse(c);
            ogCourseModels.add(temp);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CourseCatalogue.getInstance().registerListener((course, modifyType) -> binding.rv.setAdapter(new CourseRecyclerViewAdapter(CourseCatalogue.getInstance().getCourses())));

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

        binding.extendedFab.setOnClickListener(view -> {
            ArrayList <Course> courses = new ArrayList<>();
            for (CourseModel courseModel : ((CourseRecyclerViewAdapter)binding.rv.getAdapter()).getCourses()) {

                if (courseModel.getChecked()) {
                    courses.add(courseModel.getCourse());
                }

            }
            CourseUtils.addTakenCourses(courses);
            String[] takenCoursesUID = new String[courses.size()];
            for (int i = 0; i < courses.size(); i++) {
                takenCoursesUID[i] = courses.get(i).getUID();
            }
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(ChooseCoursesTakenFragmentDirections.actionChooseCoursesTakenFragmentToStudentHomescreenFragment(takenCoursesUID));
        });


        binding.topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.search:
                    SearchView searchView = (SearchView) item.getActionView();
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            if (newText.isEmpty() && (ogCourseModels.size() == ((CourseRecyclerViewAdapter)binding.rv.getAdapter()).getCourses().size())) ogCourseModels = ((CourseRecyclerViewAdapter)binding.rv.getAdapter()).getCourses();
                            filterNameCode(newText, ogCourseModels);
                            return false;
                        }
                    });
                    break;
            }
            return true;
        });

        return binding.getRoot();
    }




    private void filterNameCode(String text, ArrayList<CourseModel> ogCourseModels) {
        ArrayList<CourseModel> courseModels = ((CourseRecyclerViewAdapter) binding.rv.getAdapter()).getCourses();

        if (text.isEmpty()) {
            // Return to previous state
            ArrayList<CourseModel> coursesFromDb = new ArrayList<>();
            for (Course c : CourseCatalogue.getInstance().getCourses()) {
                CourseModel temp = new CourseModel();
                temp.setCourse(c);
                for (CourseModel cM : ogCourseModels) {
                    if (cM.getCourse().getUID().equals(c.getUID()) && cM.getChecked())
                        temp.setChecked(true);
                }
                coursesFromDb.add(temp);
            }
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(coursesFromDb));
            return;
        }
        ArrayList<CourseModel> filteredCourseModelList = new ArrayList<>();
        // Filter on name or course code
        for (CourseModel courseModel : ogCourseModels) {
            if (courseModel.getCourse().getName().toLowerCase().contains(text.toLowerCase()) || courseModel.getCourse().getCode().toLowerCase().contains(text.toLowerCase())) {
                filteredCourseModelList.add(courseModel);
                ArrayList<CourseModel> currentItems = ((CourseRecyclerViewAdapter) binding.rv.getAdapter()).getCourses();
                currentItems.indexOf(courseModel);
            }
        }
        if (!filteredCourseModelList.isEmpty()) {
            // Pass filtered list to adapter
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(filteredCourseModelList));
        } else {
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(new ArrayList<>()));
        }
    }
}