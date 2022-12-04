package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
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
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.lists.CourseListAdapter;
import org.b07boys.walnut.databinding.FragmentAdminHomescreenBinding;
import org.b07boys.walnut.main.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomescreenFragment extends Fragment {

    private FragmentAdminHomescreenBinding binding;
    private ArrayAdapter<CourseListAdapter> adapter;

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
        ((MainActivity)getActivity()).setActionBarTitles("Walnut", "Admin Page");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private ArrayAdapter<CourseListAdapter> updateAdapter() {

        ArrayList<CourseListAdapter> courses1 = new ArrayList<>();
        for (Course c : CourseCatalogue.getInstance().getCourses()) {
            CourseListAdapter temp2 = new CourseListAdapter(c);
            courses1.add(temp2);
        }

        return new ArrayAdapter<>(getActivity(),
                R.layout.activity_listview, courses1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = updateAdapter();

        CourseCatalogue.getInstance().registerListener((course, modifyType) -> {
            adapter = updateAdapter();
            binding.courseList.setAdapter(adapter);
        });


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
                            .setCourseCode(temp.getCourse().getCode()).setCourseTitle(temp.getCourse().getName())
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


