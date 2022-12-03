package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentGeneratedTimelinesBinding;
import org.b07boys.walnut.timeline.GenerateTimeline;
import org.b07boys.walnut.timeline.Timeline;
import org.b07boys.walnut.timeline.ValidTimelines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneratedTimelinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneratedTimelinesFragment extends Fragment {
    FragmentGeneratedTimelinesBinding binding;

    ArrayList<Timeline> timelines;
    ListIterator<Timeline> timelineIterator;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GeneratedTimelinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneratedTimelinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneratedTimelinesFragment newInstance(String param1, String param2) {
        GeneratedTimelinesFragment fragment = new GeneratedTimelinesFragment();
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
        binding = FragmentGeneratedTimelinesBinding.inflate(inflater, container, false);

        binding.previous.setVisibility(View.INVISIBLE);
        binding.next.setVisibility(View.INVISIBLE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        binding.generate.setOnClickListener(view13 -> {
            binding.generate.setVisibility(View.INVISIBLE);
            binding.fragGenTimelines.setText("Please wait. Generating timelines...");

            int maxCoursesInt;
            try{
                maxCoursesInt = Integer.parseInt(binding.maxCourses.getText().toString());
            }catch(Exception e){
                maxCoursesInt = 5;
            }
            binding.maxCourses.setVisibility(View.INVISIBLE);


            timelines = GenerateTimeline.generateTimeline(maxCoursesInt, SessionType.FALL);
            //ValidTimelines.getInstance().setValidTimelines(timelines);
            timelineIterator = timelines.listIterator();

            if(timelineIterator.hasNext()) {
                binding.fragGenTimelines.setText(GenerateTimeline.formatAsText(timelineIterator.next()));


                binding.next.setVisibility(View.VISIBLE);
            }else{
                binding.fragGenTimelines.setText("NO VALID TIMELINES FOUND");
            }
        });

        binding.previous.setOnClickListener(view1 -> {
            if(timelineIterator.hasPrevious()){
                binding.fragGenTimelines.setText(GenerateTimeline.formatAsText(timelineIterator.previous()));
                binding.next.setVisibility(View.VISIBLE);
            }

            if(!timelineIterator.hasNext()){
                binding.next.setVisibility(View.INVISIBLE);
            }
        });

        binding.next.setOnClickListener(view12 -> {
            if(timelineIterator.hasNext()){
                binding.fragGenTimelines.setText(GenerateTimeline.formatAsText(timelineIterator.next()));
                binding.previous.setVisibility(View.VISIBLE);
            }
            if(!timelineIterator.hasPrevious()){
                binding.previous.setVisibility(View.INVISIBLE);
            }
        });
    }


}