package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
    private ArrayList<User> followingList;
    private ArrayList<String> followedUserStringMessage;
    private dataControler controller;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        loadFromFile();

        //Print to list view. For each followed user, print his name and his most recent mood with mood date
        //just print followeduser name if no moods have been recorded
        myFriendsListView = (ListView) findViewById(R.id.myFriendsListView);
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
    }

    public void findFriends(View view){
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
            controller = new dataControler(firstUser);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


}
