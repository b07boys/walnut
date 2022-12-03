package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.b07boys.walnut.R;
import org.b07boys.walnut.databinding.FragmentStudenthomescreenBinding;
import org.b07boys.walnut.main.MainActivity;
import org.b07boys.walnut.user.TakenCourses;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentHomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentHomescreenFragment extends Fragment {

    private @NonNull FragmentStudenthomescreenBinding binding;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitles("Walnut", "Student Page");

        TakenCourses.getInstance();

        // Inflate the layout for this fragment
        binding = FragmentStudenthomescreenBinding.inflate(inflater, container, false);
        binding.addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //CourseUtils.removeTakenCourse(CourseCatalogue.getInstance().getCourseByUID("test2_uid"));

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(StudentHomescreenFragmentDirections.actionStudentHomescreenFragmentToChooseCoursesDesiredFragment());
            }
        });
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
}