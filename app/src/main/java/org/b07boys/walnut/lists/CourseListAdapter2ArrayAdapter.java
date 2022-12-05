package org.b07boys.walnut.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.CourseUtils;
import org.b07boys.walnut.user.TakenCourses;

import java.util.ArrayList;

public class CourseListAdapter2ArrayAdapter extends ArrayAdapter<CourseListAdapter2> {

    public CourseListAdapter2ArrayAdapter(Context context, int resource, ArrayList<CourseListAdapter2> courseListAdapter2s) {
        super(context, resource, courseListAdapter2s);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CourseListAdapter2 courseListAdapter2 = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_course_taken, parent, false);
        }

        TextView courseTaken = convertView.findViewById(R.id.courseName);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        courseTaken.setText(courseListAdapter2.toString());

        deleteButton.setOnClickListener(buttonView -> CourseUtils.removeTakenCourse(courseListAdapter2.getCourse()));

        return convertView;
    }

}
