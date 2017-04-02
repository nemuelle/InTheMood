package com.example.austin.inthemood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;

public class FriendRequests extends AppCompatActivity {

    private dataControler controller;
    private ArrayAdapter<String> followAdapter;
    private ArrayAdapter<String> followerAdapter;
    private ListView pendingFollowRequests;
    private ListView pendingFollowerRequests;
    private TextView followRequests;
    private TextView followerRequests;

    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        loadFromFile();

        //update current user from elasticSearch
        //User updatedCurrentUser = controller.getElasticSearchUser(controller.getCurrentUser().getName());
        //controller.updateUserList(updatedCurrentUser);
        controller.setCurrentUser(controller.addFollowerRequestsToUser(controller.getCurrentUser()));
        saveInFile();

        Gson gson = new Gson();
        Log.i("json", gson.toJson(controller.getCurrentUser()));

        //print textview
        followerRequests = (TextView) findViewById(R.id.followerRequests);
        followRequests = (TextView) findViewById(R.id.followRequests);
        followerRequests.setText("Follower Requests");
        followRequests.setText("Follow Requests");

        //print follow requests to listview
        pendingFollowRequests = (ListView) findViewById(R.id.pendingFollowRequests);
        followAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item, controller.getCurrentUser().getMyFollowRequests());
       pendingFollowRequests.setAdapter(followAdapter);

        //print follower requests to listview
        pendingFollowerRequests = (ListView) findViewById(R.id.pendingFollowerRequests);
        followerAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item, controller.getCurrentUser().getMyFollowerRequests());
        pendingFollowerRequests.setAdapter(followerAdapter);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        //update current user from elasticSearch
        User updatedCurrentUser = controller.getElasticSearchUser(controller.getCurrentUser().getName());
        controller.updateUserList(updatedCurrentUser);
        saveInFile();
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

    //load the data controller. called at the start of the activity. All data is stored in the controller.
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type objectType = new TypeToken<dataControler>() {
            }.getType();
            controller = gson.fromJson(in, objectType);
        } catch (FileNotFoundException e) {
            User firstUser = new User("admin", "admin");
            controller = new dataControler(firstUser);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
