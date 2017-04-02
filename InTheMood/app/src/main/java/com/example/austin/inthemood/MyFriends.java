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
    private ArrayList<Mood> sortedFollowingMoods = new ArrayList<Mood>();
    private ArrayList<String> followedUserStringMessage;
    private dataControler controller;
    private MoodAdapter adapter;
    //private ArrayAdapter<String> adapter;
    private User testUser;
    private Mood testMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        loadFromFile();

        testUser = new User("Steve","1");
        testMood = new Mood("Testing");
        testMood.setMoodName("Anger");
        testMood.setOwnerName("Steve");
        testUser.addMood(testMood);

        controller.addToUserList(testUser);
        controller.getCurrentUser().addToMyFollowingList("Steve");

        //Print to list view. For each followed user, print his name and his most recent mood with mood date
        //just print followed user name if no moods have been recorded
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
                sortedFollowingMoods.add(followedUserMoods.get(followedUserMoods.size() - 1));
                /*String message = followingList.get(i).getName() + " felt " +
                       followedUserMoods.get(followedUserMoods.size() - 1).getMoodName() + " on " +
                        followedUserMoods.get(followedUserMoods.size() - 1).getMoodDate();
                followedUserStringMessage.add(message);*/
            } else {
                String message = followingList.get(i).getName();
                followedUserStringMessage.add(message);

            }
        }
        adapter = new MoodAdapter(this, sortedFollowingMoods,controller.getCurrentUser().getName());
        //adapter = new ArrayAdapter<String>(this,
          //     R.layout.list_item, followedUserStringMessage);
        myFriendsListView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
    }

    /**
     * onclick findFriends button is pressed and the FindFriend activity is called
     *
     * @param view
     */
    public void findFriends(View view){
        Intent intent = new Intent(this, FindFriends.class);
        startActivity(intent);
    }

    /**
     * onclick friendRequests button is pressed and the friendRequests activity is called
     *
     * @param view
     */
    public void friendRequests(View view){
        Intent intent = new Intent(this, FriendRequests.class);
        startActivity(intent);
    }

    public void openMap(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("activity", "MyFriends");
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
