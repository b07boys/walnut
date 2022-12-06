package org.b07boys.walnut.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.b07boys.walnut.courses.SessionType;
import org.b07boys.walnut.databinding.FragmentGeneratedTimelinesBinding;
import org.b07boys.walnut.timeline.GenerateTimeline;
import org.b07boys.walnut.timeline.Timeline;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneratedTimelinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneratedTimelinesFragment extends Fragment {
    FragmentGeneratedTimelinesBinding binding;

    ArrayList<Timeline> timelines;
    ListIterator<Timeline> timelineIterator;

    public GeneratedTimelinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment GeneratedTimelinesFragment.
     */
    public static GeneratedTimelinesFragment newInstance(String param1, String param2) {
        return new GeneratedTimelinesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        AtomicReference<SessionType> sessionType = new AtomicReference<>(SessionType.FALL);

        binding.fall.setAlpha(0.5f);
        binding.fall.setClickable(false);

        binding.generate.setOnClickListener(view13 -> {
            binding.generate.setVisibility(View.INVISIBLE);
            binding.fragGenTimelines.setText("Please wait. Generating timelines...");
            binding.fall.setVisibility(View.INVISIBLE);
            binding.winter.setVisibility(View.INVISIBLE);
            binding.summer.setVisibility(View.INVISIBLE);
            binding.startingSessionText.setVisibility(View.INVISIBLE);

            int maxCoursesInt;
            try{
                maxCoursesInt = Integer.parseInt(binding.maxCourses.getText().toString());
            }catch(Exception e){
                maxCoursesInt = 5;
            }
            binding.maxCourses.setVisibility(View.INVISIBLE);
            binding.maxCoursesLayout.setVisibility(View.GONE);

            if(!GenerateTimeline.checkPrereqs()){
                binding.fragGenTimelines.setText("MISSING PREREQS - NO VALID TIMELINES FOUND");
                return;
            }

            timelines = GenerateTimeline.generateTimeline(maxCoursesInt, sessionType.get());
            //ValidTimelines.getInstance().setValidTimelines(timelines);
            timelineIterator = timelines.listIterator();

            if(timelineIterator.hasNext()) {
                binding.fragGenTimelines.setText(GenerateTimeline.formatAsText(timelineIterator.next()));

            }else{
                binding.fragGenTimelines.setText("NO VALID TIMELINES FOUND");
            }

            if(timelineIterator.hasNext()){
                binding.next.setVisibility(View.VISIBLE);
            }
        });

        //previous button
        binding.previous.setOnClickListener(view1 -> {
            if(timelineIterator.hasPrevious()){
                binding.fragGenTimelines.setText(GenerateTimeline.formatAsText(timelineIterator.previous()));
                binding.next.setVisibility(View.VISIBLE);
            }

            if(!timelineIterator.hasPrevious()){
                binding.previous.setVisibility(View.INVISIBLE);
            }
        });

        //next button
        binding.next.setOnClickListener(view12 -> {
            if(timelineIterator.hasNext()){
                binding.fragGenTimelines.setText(GenerateTimeline.formatAsText(timelineIterator.next()));
                binding.previous.setVisibility(View.VISIBLE);
            }
            if(!timelineIterator.hasNext()){
                binding.next.setVisibility(View.INVISIBLE);
            }
        });

        //fall button
        binding.fall.setOnClickListener(view14 -> {
            sessionType.set(SessionType.FALL);

            binding.fall.setClickable(false);
            binding.winter.setClickable(true);
            binding.summer.setClickable(true);

            binding.fall.setAlpha(0.5f);
            binding.winter.setAlpha(1f);
            binding.summer.setAlpha(1f);
        });

        //winter button
        binding.winter.setOnClickListener(view15 -> {
            sessionType.set(SessionType.WINTER);

            binding.fall.setClickable(true);
            binding.winter.setClickable(false);
            binding.summer.setClickable(true);

            binding.fall.setAlpha(1f);
            binding.winter.setAlpha(0.5f);
            binding.summer.setAlpha(1f);
        });

        //summer button
        binding.summer.setOnClickListener(view16 -> {
            sessionType.set(SessionType.SUMMER);

            binding.fall.setClickable(true);
            binding.winter.setClickable(true);
            binding.summer.setClickable(false);

            binding.fall.setAlpha(1f);
            binding.winter.setAlpha(1f);
            binding.summer.setAlpha(0.5f);
        });

    }


}