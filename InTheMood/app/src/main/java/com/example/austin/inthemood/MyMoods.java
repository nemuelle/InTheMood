package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
/* My moods activity displays the list of all moods a user inputs and allows filtering the list to
    show only relevant moods.

    For project part 5:
        Still need to change each mood item's color based on what mood it is
        Still need to connect new mood button to add/edit mood activity
        Still need to make clicking a mood take you to its edit screen.
        Still need to make the buttons appear unselected upon 2nd click
 */
public class MyMoods extends AppCompatActivity {


    //UI Elements
    private Button emotionFilterButton;
    private Button weekFilterButton;
    private Button triggerFilterButton;
    private EditText triggerText;
    private ImageButton newMoodButton;
    private ImageButton calendarButton;
    private ImageButton mapButton;
    private Spinner moodFilterSpinner;
    private ListView moodsListView;
    public dataControler controller;
    private static final String FILENAME = "file.sav";
    private ArrayAdapter<Mood> moodAdapter;
    private User currentUser;
    private ArrayList<Mood> SortedMoodList = new ArrayList<Mood>();
    private ArrayList<Mood> NewMoodList = new ArrayList<Mood>();
    private ArrayList<Mood> OriginalMoodList = new ArrayList<Mood>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_moods);

        //Initialize UI elements
        emotionFilterButton = (Button) findViewById(R.id.emotionalStateFilterButton);
        weekFilterButton = (Button) findViewById(R.id.weekFilterButton);
        triggerFilterButton = (RadioButton) findViewById(R.id.triggerFilterButton);
        triggerText = (EditText) findViewById(R.id.triggerFilterEditText);
        moodFilterSpinner = (Spinner) findViewById(R.id.moodFilterSpinner);
        moodsListView = (ListView) findViewById(R.id.myMoodsListView);
        newMoodButton = (ImageButton) findViewById(R.id.newMoodImg);
        calendarButton = (ImageButton) findViewById(R.id.calendarImg);
        mapButton = (ImageButton) findViewById(R.id.mapImg);

        ArrayAdapter<CharSequence> moodSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.moods, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        moodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        moodFilterSpinner.setAdapter(moodSpinnerAdapter);


        //onclick listener to apply/deapply trigger mood filter.
        triggerFilterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                if (triggerFilterButton.isSelected() == false) {
                    NewMoodList = controller.filterByTrigger(triggerText.getText().toString(), SortedMoodList);
                    SortedMoodList.clear();

                    for (int i = 0; i < NewMoodList.size(); i++) {
                        SortedMoodList.add(NewMoodList.get(i));
                    }

                    moodAdapter.notifyDataSetChanged();
                    triggerFilterButton.setSelected(true);

                }
                else{
                    //Unapply trigger filter while keeping any other filters selected active
                    triggerFilterButton.setSelected(false);
                    SortedMoodList.clear();


                    if (emotionFilterButton.isSelected()){
                        NewMoodList = controller.filterByMood(moodFilterSpinner.getSelectedItem().toString(),OriginalMoodList);
                    }

                    if (weekFilterButton.isSelected()){
                        if (emotionFilterButton.isSelected()){
                            NewMoodList = controller.filterByWeek(NewMoodList);
                        }
                        else{
                            NewMoodList = controller.filterByWeek(OriginalMoodList);

                        }
                    }

                    emotionFilterButton.setSelected(true);

                    if (emotionFilterButton.isSelected() == false && weekFilterButton.isSelected() == false){
                        for (int i = 0; i < OriginalMoodList.size(); i++) {
                            SortedMoodList.add(OriginalMoodList.get(i));
                        }
                    }else{
                        for (int i = 0; i < NewMoodList.size(); i++) {
                            SortedMoodList.add(NewMoodList.get(i));
                        }
                    }
                    moodAdapter.notifyDataSetChanged();


                }
            }

        });
        //Apply last week filter for moods
        weekFilterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                if (!weekFilterButton.isSelected()) {
                    NewMoodList = controller.filterByWeek(currentUser.getMyMoodsList());
                    SortedMoodList.clear();
                    for (int i = 0; i < NewMoodList.size(); i++) {
                        SortedMoodList.add(NewMoodList.get(i));
                    }
                    weekFilterButton.setSelected(true);
                }else{

                    //unapply last week filter for moods and keep and other active filters
                    if (emotionFilterButton.isSelected()){
                        NewMoodList = controller.filterByMood(moodFilterSpinner.getSelectedItem().toString(),OriginalMoodList);
                    }

                    if (triggerFilterButton.isSelected()){
                        if (emotionFilterButton.isSelected()){
                            NewMoodList = controller.filterByTrigger(triggerText.getText().toString(),NewMoodList);
                        }
                        else{
                            NewMoodList = controller.filterByTrigger(triggerText.getText().toString(),OriginalMoodList);
                        }
                    }
                    weekFilterButton.setSelected(false);
                    SortedMoodList.clear();
                    if (emotionFilterButton.isSelected() == false && triggerFilterButton.isSelected() == false){
                        for (int i = 0; i < OriginalMoodList.size(); i++) {
                            SortedMoodList.add(OriginalMoodList.get(i));
                        }
                    }else{
                        for (int i = 0; i < NewMoodList.size(); i++) {
                            SortedMoodList.add(NewMoodList.get(i));
                        }
                    }

                }

                moodAdapter.notifyDataSetChanged();
            }

        });
        //apply selected emotion filter
        emotionFilterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                if (!emotionFilterButton.isSelected()) {
                    emotionFilterButton.setSelected(true);
                    NewMoodList = controller.filterByMood(moodFilterSpinner.getSelectedItem().toString(), currentUser.getMyMoodsList());
                    SortedMoodList.clear();

                    for (int i = 0; i < NewMoodList.size(); i++) {
                        SortedMoodList.add(NewMoodList.get(i));
                    }
                    moodAdapter.notifyDataSetChanged();

                }else{
                    //unapply emotion filter and keep other active filters
                    if (weekFilterButton.isSelected()){
                        NewMoodList = controller.filterByWeek(OriginalMoodList);
                    }
                    if (triggerFilterButton.isSelected()){
                        if (weekFilterButton.isSelected()){
                            if (emotionFilterButton.isSelected()){
                                NewMoodList = controller.filterByTrigger(triggerText.getText().toString(),NewMoodList);
                            }
                            else{
                                NewMoodList = controller.filterByTrigger(triggerText.getText().toString(),OriginalMoodList);
                            }
                        }
                    }
                    emotionFilterButton.setSelected(false);
                    SortedMoodList.clear();
                    if (triggerFilterButton.isSelected() == false && weekFilterButton.isSelected() == false){
                        for (int i = 0; i < OriginalMoodList.size(); i++) {
                            SortedMoodList.add(OriginalMoodList.get(i));
                        }
                    }else{
                        for (int i = 0; i < NewMoodList.size(); i++) {
                            SortedMoodList.add(NewMoodList.get(i));
                        }
                    }

                }

                //triggerText.setText(moodFilterSpinner.getSelectedItem().toString());

                moodAdapter.notifyDataSetChanged();
            }

        });


        newMoodButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                addMood(v);
            }

        });

        calendarButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // send moods to the new activity

            }

        });

        mapButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // send moods to the new activity

            }

        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        currentUser = controller.getCurrentUser();
        SortedMoodList = currentUser.getMyMoodsList();

        //store a copy of original mood list to allow easier unapplying of filters
        for (int i=0; i < currentUser.getMyMoodsList().size(); i++ ){
            OriginalMoodList.add(currentUser.getMyMoodsList().get(i));
        }

        moodAdapter = new ArrayAdapter<Mood>(this,R.layout.list_item,SortedMoodList);
        moodsListView.setAdapter(moodAdapter);
        moodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View view, int position,long id){
                editMood(view,position);
            }
        });
    }


    //Start edit mood activity
    private void editMood(View view, int index){
        Intent editMoodIntent = new Intent(this,addEditMood.class);
        editMoodIntent.putExtra("Mood index",index);
        startActivity(editMoodIntent);


    }
    //Start activity to add another mood
    private void addMood(View view){
        Intent addMoodIntent = new Intent(this,addEditMood.class);
        startActivity(addMoodIntent);


    }

    //Load data controller
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type objectType = new TypeToken<dataControler>() {}.getType();
            controller = gson.fromJson(in, objectType);
        } catch (FileNotFoundException e) {
            User firstUser = new User("admin", "admin");
            controller = new dataControler(firstUser);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
