package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainUser extends AppCompatActivity {
    private MainUser activity = this;

    //UI Elements
    private Button myFriendsMoodsButton;
    private Button myMoodsButton;
    private Button myFriendsButton;
    private Button signOutButton;

    private static final String FILENAME = "file.sav";
    public dataControler controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        //Initialize UI Elements
        myFriendsMoodsButton = (Button) findViewById(R.id.myFriendsMoodsButton);
        myMoodsButton = (Button) findViewById(R.id.myMoodsButton);
        myFriendsButton = (Button) findViewById(R.id.myFriendsButton);
        signOutButton = (Button) findViewById(R.id.signOutButton);

        loadFromFile();
        String message = controller.getCurrentUser().getName();
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);



        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main_user);
        layout.addView(textView);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
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
