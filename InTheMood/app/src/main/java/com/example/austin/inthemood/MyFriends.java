package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * This class displays the current user's Friends and their most recent mood (with date) to the list
 * view.
 */
public class MyFriends extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView myFriendsListView;
    private TextView offlineMessage;
    private ArrayList<User> followingList;
    private ArrayList<String> followedUserStringMessage;
    private dataControler controller;
    private ArrayAdapter<String> adapter;
    //Testing Variables
    private User testUser;
    private User testUser2;
    private User testUser3;
    private User testUser4;
    private Mood moodTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        loadFromFile();

        testUser = new User("Test","Test");
        moodTest = new Mood("Testing");
        moodTest.setMoodName("Anger");
        testUser.addMood(moodTest);
        controller.addToUserList(testUser);
        controller.getCurrentUser().addToMyFollowingList("Test");

        myFriendsListView = (ListView) findViewById(R.id.myFriendsListView);
        offlineMessage = (TextView) findViewById(R.id.offlineMessage);
        followingList = new ArrayList<User>();
        for (int i = 0; i < controller.getCurrentUser().getMyFollowingList().size(); i++){
            followingList.add(controller.searchForUserByName(controller.getCurrentUser().getMyFollowingList().get(i)));
        }
        followedUserStringMessage = new ArrayList<String>();
        for (int i = 0; i < followingList.size(); i++){
            ArrayList<Mood> followedUserMoods = followingList.get(i).getMyMoodsList();

            //if the followed user has moods, find his most recent mood and display it. If not,
            //only display his name
            if (followedUserMoods.size() > 0) {
                followedUserMoods = controller.sortMoodsByDate(followedUserMoods);
                String message = followingList.get(i).getName() + " felt " +
                        followedUserMoods.get(followedUserMoods.size() - 1).getMoodName() + " on " +
                        followedUserMoods.get(followedUserMoods.size() - 1).getMoodDate();
                followedUserStringMessage.add(message);
            } else {
                String message = followingList.get(i).getName();
                followedUserStringMessage.add(message);

            }
        }
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, followedUserStringMessage);
        myFriendsListView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        //If we're not online, hide the friend information as it may be out of date.
        //TODO: have a proper connection checker
        if (false) {
            myFriendsListView.setVisibility(View.GONE);
            offlineMessage.setVisibility(View.VISIBLE);
        }   else {
            //We are online, so show your friends' most recent mood.
            myFriendsListView.setVisibility(View.VISIBLE);
            offlineMessage.setVisibility(View.GONE);
        }
    }

    //Called when the Find Friends button is clicked.
    public void findFriend(View view){
        Intent intent = new Intent(this, FindFriends.class);
        startActivity(intent);
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
            controller = new dataControler(firstUser, this);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
