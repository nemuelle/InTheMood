package com.example.austin.inthemood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MyMoods extends AppCompatActivity {


    //UI Elements
    private Button emotionFilterButton;
    private Button weekFilterButton;
    private Button triggerFilterButton;
    private EditText triggerText;
    private Spinner moodFilterSpinner;
    private ListView moodsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_moods);

        //Initialize UI elements
        emotionFilterButton = (Button) findViewById(R.id.emotionalStateFilterButton);
        weekFilterButton = (Button) findViewById(R.id.weekFilterButton);
        triggerFilterButton = (Button) findViewById(R.id.triggerFilterButton);
        triggerText = (EditText) findViewById(R.id.triggerFilterEditText);
        moodFilterSpinner = (Spinner) findViewById(R.id.moodFilterSpinner);
        moodsListView = (ListView) findViewById(R.id.myMoodsListView);


    }
}
