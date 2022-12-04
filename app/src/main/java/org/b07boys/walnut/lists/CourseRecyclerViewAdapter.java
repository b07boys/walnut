package org.b07boys.walnut.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.b07boys.walnut.R;
import org.b07boys.walnut.courses.Course;
import org.b07boys.walnut.courses.SessionType;

import java.util.ArrayList;
import java.util.Set;

public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CourseModel> courses;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descTextView;
        private final TextView codeTextView;
        private final MaterialCardView cardView;
        private ArrayList<Course> courses;

        public ViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            descTextView = (TextView) view.findViewById(R.id.descTextView);
            codeTextView = (TextView) view.findViewById(R.id.codeTextView);
            cardView = (MaterialCardView) view.findViewById(R.id.card);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardView.toggle();
                }
            });
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
        public MaterialCardView getCardView() {
            return cardView;
        }
        public TextView getDescTextView() {
            return descTextView;
        }
        public TextView getCodeTextView() {
            return codeTextView;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CourseRecyclerViewAdapter(Set<Course> dataSet) {
        courses = new ArrayList<>();
        for (Course c: dataSet) {
            CourseModel a = new CourseModel();
            a.setCourse(c);
            courses.add(a);
        }
    }

    /*public CourseListAdapter(ArrayList<Course> dataSet) {
        courses = new ArrayList<>();
        for (Course c : dataSet) {
            CourseModel a = new CourseModel();
            a.setCourse(c);
            courses.add(a);
        }
    }*/

    public CourseRecyclerViewAdapter(ArrayList<CourseModel> dataSet) {
        this.courses = dataSet;
    }

    public ArrayList<CourseModel> getCourses() {
        return courses;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        final CourseModel data = courses.get(position);

        viewHolder.cardView.setOnCheckedChangeListener(null);
        viewHolder.cardView.setChecked(data.getChecked());
        viewHolder.cardView.setOnCheckedChangeListener(new MaterialCardView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialCardView card, boolean isChecked) {
                data.setChecked(isChecked);
            }
        });

        viewHolder.getTitleTextView().setText(courses.get(position).getCourse().getName());
        SessionType[] stuff = courses.get(position).getCourse().getOfferingSessions();
        String text = "";
        for (SessionType e : stuff) {
            text += (e.toString() + ", ");
        }
        text = text.substring(0, text.length()-2);
        viewHolder.getDescTextView().setText(text);
        viewHolder.getCodeTextView().setText(courses.get(position).getCourse().getCode());
        //viewHolder.getDescTextView().setText(courses.get(position).getCourse().getCode());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courses.size();
    }

}
