package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.ModifyCourseType;
import org.b07boys.walnut.courses.OnChangeCourseListener;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentChooseCoursesDesiredBinding;
import org.b07boys.walnut.lists.CourseModel;
import org.b07boys.walnut.lists.CourseRecyclerViewAdapter;
import org.b07boys.walnut.main.MainActivity;
import org.b07boys.walnut.user.DesiredCourses;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseCoursesDesiredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCoursesDesiredFragment extends Fragment {

    FragmentChooseCoursesDesiredBinding binding;

    public ChooseCoursesDesiredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment ChooseCoursesDesiredFragment.
     */
    public static ChooseCoursesDesiredFragment newInstance() {
        return new ChooseCoursesDesiredFragment();
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

        ((MainActivity)getActivity()).setActionBarTitles("Walnut", "Choose courses you wish to take");

        DesiredCourses.getInstance().getCourses().clear();

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


        binding.toggleButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.button1) {
                if (isChecked) filterSession("FALL");
                else binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
            } else if (checkedId == R.id.button2) {
                if (isChecked) filterSession("WINTER");
                else binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
            } else if (checkedId == R.id.button3) {
                if (isChecked) filterSession("SUMMER");
                else binding.rv.setAdapter(new CourseRecyclerViewAdapter(courseModels));
            }
        });

        binding.saveSelectedCoursesFab.setOnClickListener(view -> {
            for (CourseModel courseModel : ((CourseRecyclerViewAdapter)binding.rv.getAdapter()).getCourses()) {
                if (courseModel.getChecked()) DesiredCourses.getInstance().addCourse(courseModel.getCourse());
            }

            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(ChooseCoursesDesiredFragmentDirections.actionChooseCoursesDesiredFragmentToGeneratedTimelinesFragment());
        });

        return binding.getRoot();
    }

    private void filterSession(String text) {
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
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(new ArrayList<>()));
        }
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
                    if (cM.getCourse().getUID().equals(c.getUID()) && cM.getChecked()) temp.setChecked(true);
                }
                coursesFromDb.add(temp);
            }
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(coursesFromDb));
            return;
        }
        ArrayList<CourseModel> filteredCourseModelList = new ArrayList<>();
        // Filter on name or course code
        for (CourseModel courseModel : ogCourseModels) {
            if (courseModel.getCourse().getName().toLowerCase().contains(text.toLowerCase()) || courseModel.getCourse().getCode().toLowerCase().contains(text.toLowerCase()))
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
            binding.rv.setAdapter(new CourseRecyclerViewAdapter(new ArrayList<>()));
        }
    }

}