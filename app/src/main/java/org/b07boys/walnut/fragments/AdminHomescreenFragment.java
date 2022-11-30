package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.CourseCatalog;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.database.DatabaseNode;
import org.b07boys.walnut.database.DatabaseNodeEditor;
import org.b07boys.walnut.database.DatabasePaths;
import org.b07boys.walnut.database.adapters.CourseAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomescreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminHomescreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminHomescreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminHomescreenFragment newInstance(String param1, String param2) {
        AdminHomescreenFragment fragment = new AdminHomescreenFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_homescreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //THIS IS JUST A TEST, YOU CAN SAFELY DELETE THIS AND THE BUTTON ASSOCIATED
        Button button = view.findViewById(R.id.init_course_catalogue);
        button.setOnClickListener(clickView -> {
            CourseCatalog.getInstance();
            CourseUtils.createCourse(
                    "CSCA57676",
                    "theory of man",
                    new SessionType[]{SessionType.SUMMER},
                    new String[]{"so_funny"}
            ).addOnSuccessListener(task -> {
                Log.d("CREATE_SUCCESS", "Added!");
            }).addOnFailureListener(exception -> {
                Log.w("CREATE_FAILURE", "failed create", exception);
            });
        });

    }

}