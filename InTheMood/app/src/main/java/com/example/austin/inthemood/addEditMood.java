package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class addEditMood extends AppCompatActivity {
    private addEditMood activity = this;
    private dataControler controller;
    private String FILENAME = "file.sav";

    //UI Elements
    private Spinner moodSpinner;
    private Spinner scenarioSpinner;
    private EditText triggerText;
    private Button saveButton;
    private Button deleteButton;

    //Mood Index
    int moodIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_mood);

        //Initialize UI Elements
        moodSpinner = (Spinner) findViewById(R.id.addEditMoodMoodSpinner);
        scenarioSpinner = (Spinner) findViewById(R.id.addEditMoodScenarioSpinner);
        triggerText = (EditText) findViewById(R.id.addEditMoodsTriggerText);
        saveButton = (Button) findViewById(R.id.addEditMoodSaveButton);
        deleteButton = (Button) findViewById(R.id.addEditMoodDeleteButton);

        //Grab the data controller
        loadFromFile();

        //Check if a mood was passed in
        Intent intent = getIntent();
        //TODO: be able to receive a mood index from caller
        //moodIndex = intent.getIntExtra(MyMoods.edit_mood, -1);
        if (moodIndex != -1) {
            //TODO: grab respective mood
        }


        /*
         * Spinner initialization shamelessly taken from https://developer.android.com/guide/topics/ui/controls/spinner.html
         */

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> moodAdapter = ArrayAdapter.createFromResource(this,
                R.array.moods, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        moodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        moodSpinner.setAdapter(moodAdapter);

        ArrayAdapter<CharSequence> socialAdapter = ArrayAdapter.createFromResource(this,
                R.array.social_situations, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        scenarioSpinner.setAdapter(socialAdapter);

        /*
         * Code that is run when the Save button is clicked. Saves the user input and creates a mood
         * and adds it to the list of moods made by the Current User.
         *
         */

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String moodName = moodSpinner.getSelectedItem().toString();
                String scenario = scenarioSpinner.getSelectedItem().toString();
                String trigger = triggerText.getText().toString();
                Mood newMood = new Mood();
                newMood.setMoodName(moodName);
                newMood.setMoodDescription(trigger);
                controller.getCurrentUser().addMood(newMood);
                Intent intent = new Intent(activity, MyMoods.class);
                saveInFile();
                startActivity(intent);
            }
        });

        //TODO: be able to delete a mood
        /*
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.getCurrentUser().removeMood();
                Intent intent = new Intent(activity, MyMoods.class);
                saveInFile();
                startActivity(intent);
            }
        });
        */
    }

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

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(controller, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
