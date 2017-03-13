package com.example.austin.inthemood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nemuelle on 2017-03-12.
 * Define an adapter to populate the listview in MoodCalendarActivity
 * based on https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 * accessed march 12 2017
 */
public class MoodAdapter extends ArrayAdapter<Mood> {
    /**
     * Instantiates a new Mood adapter.
     *
     * @param context  the context
     * @param moodList the mood list
     */
    public MoodAdapter(Context context, ArrayList<Mood> moodList) {
        super(context, 0, moodList);
    }

    /**
     *
     * @param position, the position of the mood selected
     * @param convertView, the view inside the ListView
     * @param parent, parent view
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Mood mood = (Mood) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mood, parent, false);
        }
        // Lookup view for data population
        TextView moodString = (TextView) convertView.findViewById(R.id.moodString);
        TextView moodComment = (TextView) convertView.findViewById(R.id.moodComment);
        // Populate the data into the template view using the data object
        moodString.setText(mood.getMoodName());
        moodComment.setText(mood.getMoodDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}
