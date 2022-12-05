package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.CourseCatalogue;
import org.b07boys.walnut.courses.ModifyCourseType;
import org.b07boys.walnut.courses.OnChangeCourseListener;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.lists.CourseListAdapter;
import org.b07boys.walnut.databinding.FragmentAdminHomescreenBinding;
import org.b07boys.walnut.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomescreenFragment extends Fragment {

    private FragmentAdminHomescreenBinding binding;
    private ArrayAdapter<CourseListAdapter> adapter;
    private OnChangeCourseListener listener;

    private Map<Course, CourseListAdapter> courseMap;

    public AdminHomescreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment AdminHomescreenFragment.
     */
    public static AdminHomescreenFragment newInstance(String param1, String param2) {
        AdminHomescreenFragment fragment = new AdminHomescreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseMap = new HashMap<>();
        ((MainActivity)getActivity()).setActionBarTitles("Walnut", "Admin Page");
        WindowCompat.setDecorFitsSystemWindows(getActivity().getWindow(), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void updateCourses(CourseListAdapter courseAdapter, ModifyCourseType modifyType) {

        switch (modifyType) {

            case ADD: {
                adapter.add(courseAdapter);
                break;
            }

            case REMOVE: {
                adapter.remove(courseAdapter);
                break;
            }

        }

        adapter.notifyDataSetChanged();

    }

    private ArrayAdapter<CourseListAdapter> initializeAdapter() {

        ArrayList<CourseListAdapter> courses1 = new ArrayList<>();
        for (Course c : CourseCatalogue.getInstance().getCourses()) {
            CourseListAdapter temp2 = new CourseListAdapter(c);
            courseMap.put(c, temp2);
            courses1.add(temp2);
        }

        return new ArrayAdapter<>(getActivity(),
                R.layout.activity_listview, courses1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (adapter == null) {
            adapter = initializeAdapter();
        }

        if (listener == null) {
            listener = ((course, modifyType) -> {

                CourseListAdapter courseAdapter;

                if (courseMap.containsKey(course)) {
                    courseAdapter = courseMap.get(course);
                } else {
                    courseAdapter = new CourseListAdapter(course);
                    courseMap.put(course, courseAdapter);
                }

                updateCourses(courseAdapter, modifyType);

            });
            CourseCatalogue.getInstance().registerListener(listener);
        }

        ListView listView = (ListView) binding.courseList;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            CourseListAdapter temp = ((CourseListAdapter)adapterView.getAdapter().getItem(i));
            String text = "";
            SessionType[] stuff = temp.getCourse().getOfferingSessions();
            String[] stuff2 = new String[temp.getCourse().getOfferingSessions().length];
            for (int ind = 0; ind < stuff2.length; ind++){
                stuff2[ind] = stuff[ind].name();
            }
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                    .navigate(AdminHomescreenFragmentDirections
                            .actionAdminHomescreenFragmentToCoursePopUpFragment("",null,null,null)
                            .setCourseCode(temp.getCourse().getCode())
                            .setCourseTitle(temp.getCourse().getName())
                            .setPrerequisites(temp.getCourse().getPrerequisiteUIDS()).setCourseUID(temp.getCourse().getUID())
                            .setSessions(stuff2));
        });


        binding.addCourseButton.setOnClickListener(viewButton -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                    .navigate(AdminHomescreenFragmentDirections
                            .actionAdminHomescreenFragmentToAddCoursePopUpFragment());
        });
        SearchView search = binding.searchBar;

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SEARCH_CHANGE", newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
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
}


