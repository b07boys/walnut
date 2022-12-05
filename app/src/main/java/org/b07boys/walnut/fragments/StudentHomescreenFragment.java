package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.OnChangeCourseListener;
import org.b07boys.walnut.databinding.FragmentStudenthomescreenBinding;
import org.b07boys.walnut.lists.CourseListAdapter2;
import org.b07boys.walnut.main.MainActivity;
import org.b07boys.walnut.user.TakenCourses;
import org.b07boys.walnut.user.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentHomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentHomescreenFragment extends Fragment {

    private @NonNull FragmentStudenthomescreenBinding binding;
    private OnChangeCourseListener listener;

    public StudentHomescreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment MainPage.
     */
    public static StudentHomescreenFragment newInstance() {
        return new StudentHomescreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.getInstance().getTakenCourses();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitles("Walnut", "Student Page");

        TakenCourses.getInstance();

        // Inflate the layout for this fragment
        binding = FragmentStudenthomescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.main_top_app_bar, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void deregisterListener() {
        if (listener != null) {
            TakenCourses.getInstance().unregisterListener(listener);
            listener = null;
        }
    }

    private void updateCoursesTakenList() {
        ArrayList<CourseListAdapter2> takenCourses = new ArrayList<>();
        ArrayList<Course> databaseTakenCourses = new ArrayList<>();
        databaseTakenCourses.addAll(User.getInstance().getTakenCourses().getCourses());
        for (int i = 0; i < databaseTakenCourses.size(); i++) {
            CourseListAdapter2 temp2 = new CourseListAdapter2(databaseTakenCourses.get(i));
            takenCourses.add(temp2);
        }
        ArrayAdapter adapter = new ArrayAdapter<CourseListAdapter2>(getActivity(),
                R.layout.activity_listview, takenCourses);

        ListView listView = (ListView) binding.courseListView;
        listView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateCoursesTakenList();

        if (listener == null) {
            listener = (course, modifyType) -> {
                if (isVisible()) {
                    updateCoursesTakenList();
                }
            };
            TakenCourses.getInstance().registerListener(listener);
        }

        binding.addCourseButton.setOnClickListener(view1 -> {
            StudentHomescreenFragment.this.deregisterListener();
            Navigation.findNavController(StudentHomescreenFragment.this.getActivity(), R.id.nav_host_fragment).navigate(StudentHomescreenFragmentDirections.actionStudentHomescreenFragmentToChooseCoursesDesiredFragment());
        });

        binding.addCoursesTaken.setOnClickListener(view1 -> {
            deregisterListener();
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(StudentHomescreenFragmentDirections.actionStudentHomescreenFragmentToChooseCoursesTakenFragment());
        });

    }
}