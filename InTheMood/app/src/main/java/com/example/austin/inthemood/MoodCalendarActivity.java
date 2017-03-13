package com.example.austin.inthemood;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.austin.inthemood.decorators.EventDecorator;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class MoodCalendarActivity extends AppCompatActivity implements OnDateSelectedListener {

    private MaterialCalendarView widget;
    private ArrayList<Mood> moodListForDay;
    private ArrayList<Mood> moodListForMonth; // save from time
    private ListView moodForDayListView;
    private MoodAdapter moodArrayAdapter;

    User user;
    dataControler dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        moodForDayListView = (ListView) findViewById(R.id.moodListViewForDay);

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        widget.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        widget.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();


        new PutMoodsInMaterialCalendarView().executeOnExecutor(Executors.newSingleThreadExecutor());

        moodForDayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // send to edit mood?
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        // here i think there should be a view with the moods that happened on that day
        // maybe a listview below the calendar or have another activity to show the moods

        // find moods that occur on this day
        for (Mood mood : moodListForMonth) {
            Calendar moodDateCalendar = Calendar.getInstance();
            moodDateCalendar.setTime(mood.getMoodDate());
            if (moodDateCalendar.get(Calendar.DAY_OF_MONTH) == date.getCalendar().get(Calendar.DAY_OF_MONTH)) {
                moodListForDay.add(mood);
            }
        }

        // update adapter
        moodArrayAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onStart() {
        super.onStart();
        moodListForDay = new ArrayList<>();
        moodListForMonth = new ArrayList<>();

        moodArrayAdapter = new MoodAdapter(this, moodListForDay);
        moodForDayListView.setAdapter(moodArrayAdapter);
        moodArrayAdapter.notifyDataSetChanged(); // not sure if needed since its empty

        // init user and dataControler
    }

    /**
     * Get moods that have happened and put them in the Calendar as a red circle under the day.
     * Not quite sure how we get the moods yet
     *
     * Based on
     * https://github.com/prolificinteractive/material-calendarview/tree/master/sample/src/main/java/com/prolificinteractive/materialcalendarview/sample
     * and is Copyright (c) 2016 Prolific Interactive.
     */
    private class PutMoodsInMaterialCalendarView extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Calendar calendar = Calendar.getInstance();

            ArrayList<Mood> moods = user.getMyMoodsList();
            ArrayList<CalendarDay> dates = new ArrayList<>();


            // if the mood has the same month as current date show the moods
            // this needs to be changed to allow if the user changes the month
            // but there isn't time to figure that out right now
            for (Mood mood : moods) {
                Calendar moodDateCalendar = Calendar.getInstance();
                moodDateCalendar.setTime(mood.getMoodDate());
                if (calendar.get(Calendar.MONTH) == moodDateCalendar.get(Calendar.MONTH)) {
                    dates.add(CalendarDay.from(moodDateCalendar));
                    moodListForMonth.add(mood); // for when a user selects a date
                }
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }

    }
}
