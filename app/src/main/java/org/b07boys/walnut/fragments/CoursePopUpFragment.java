package org.b07boys.walnut.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.b07boys.walnut.courses.SessionType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CoursePopUpFragment extends BottomSheetDialogFragment {

    protected final Map<SessionType, MaterialButton> sessions;
    protected final Map<String, CheckBox> prerequisites;

    public CoursePopUpFragment() {
        sessions = new HashMap<>();
        prerequisites = new HashMap<>();
    }

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void setCheckboxes();

    protected SessionType[] getCheckedSessions() {

        Set<SessionType> sessionTypes = new HashSet<>();

        for (Map.Entry<SessionType, MaterialButton> entry : sessions.entrySet()) {
            if (entry.getValue().isChecked())
                sessionTypes.add(entry.getKey());
        }

        return sessionTypes.toArray(new SessionType[0]);

    }

    protected String[] getCheckedCourses() {

        Set<String> courses = new HashSet<>();

        for (Map.Entry<String, CheckBox> entry : prerequisites.entrySet()) {
            if (entry.getValue().isChecked())
                courses.add(entry.getKey());
        }

        return courses.toArray(new String[0]);
    }

    protected void sendSnackbar(View view, String message, int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(color);
        snackbar.show();
    }

}
