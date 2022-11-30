package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.b07boys.walnut.databinding.FragmentStudenthomescreenBinding;

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
        // Inflate the layout for this fragment
        binding = FragmentStudenthomescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}