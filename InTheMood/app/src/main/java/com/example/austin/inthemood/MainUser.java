package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

/**
 * This class is the main menu view. There are 5 buttons in the layout to choose from.
 * Each button sends the user to a different activity.
 */

public class MainUser extends AppCompatActivity {
    private MainUser activity = this;

    //UI Elements
    private Button myFriendsMoodsButton;
    private Button myMoodsButton;
    private Button myFriendsButton;
    private Button signOutButton;
    private Button moodCalendarButton;

    private static final String FILENAME = "file.sav";
    public dataControler controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        loadFromFile();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
    }
    //go to MyMoods activity 
    public void MyMoods(View view){
        Intent intent = new Intent(this, MyMoods.class);
        startActivity(intent);
    }

    //go to myFriendsMoods activity (empty activity at the moment)
    public void MyFriendsMoods(View view){
        Intent intent = new Intent(this, MyFriendsMoods.class);
        startActivity(intent);
    }

    //go back to existing loggin activity and set current user to null
    public void SignOut(View view){
        controller.signOut();
        saveInFile();
        Intent intent = new Intent(this, ExistingUserLogin.class);
        startActivity(intent);
    }

    //start the myFriends activity
    public void MyFriends(View view){
        Intent intent = new Intent(this, MyFriends.class);
        startActivity(intent);
    }


    //start the calendar activity
    public void MoodCalendar(View view) {
        Intent intent = new Intent(this, MoodCalendarActivity.class);
        startActivity(intent);
    }

    //load the data controller. called at the start of the activity. All data is stored in the controller.
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
    //save the data controller. This function is never called in here for the time being
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
