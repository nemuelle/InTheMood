package com.example.austin.inthemood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Toast;

import android.widget.RadioButton;
import android.widget.Spinner;


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

    //UI Elements
    private Button emotionFilterButton;
    private Button weekFilterButton;
    private Button triggerFilterButton;
    private Button mapButton;
    private EditText triggerText;
    private Spinner moodFilterSpinner;
    private User currentUser;
    private ArrayList<Mood> newMoodList = new ArrayList<Mood>();
    private ArrayList<Mood> originalMoodList = new ArrayList<Mood>();

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

        //Initialize UI elements
        emotionFilterButton = (Button) findViewById(R.id.emotionalStateFilterButton);
        weekFilterButton = (Button) findViewById(R.id.weekFilterButton);
        triggerFilterButton = (RadioButton) findViewById(R.id.triggerFilterButton);
        mapButton = (Button) findViewById(R.id.mapButton);

        triggerText = (EditText) findViewById(R.id.triggerFilterEditText);
        moodFilterSpinner = (Spinner) findViewById(R.id.moodFilterSpinner);
        myFriendsListView = (ListView) findViewById(R.id.myMoodsListView);

        testUser = new User("Steve","1");
        testMood = new Mood("Testing");
        testMood.setMoodName("Anger");
        testMood.setOwnerName("Steve");
        testUser.addMood(testMood);

        controller.addToUserList(testUser);
        controller.getCurrentUser().addToMyFollowingList("Steve");
        controller.setCurrentUser(controller.addFollowingToUser(controller.getCurrentUser()));

        ArrayAdapter<CharSequence> moodSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.moods, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        moodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        moodFilterSpinner.setAdapter(moodSpinnerAdapter);

        //Print to list view. For each followed user, print his name and his most recent mood with mood date
        //just print followeduser name if no moods have been recorded
        myFriendsListView = (ListView) findViewById(R.id.myFriendsListView);
        followingList = new ArrayList<User>();
        for (int i = 0; i < controller.getCurrentUser().getMyFollowingList().size(); i++){
           User user = controller.getElasticSearchUser(controller.getCurrentUser().getMyFollowingList().get(i));
           if (user != null) {
               followingList.add(user);
           }
        }
        followedUserStringMessage = new ArrayList<String>();
        for (int i = 0; i < followingList.size(); i++){
            ArrayList<Mood> followedUserMoods = followingList.get(i).getMyMoodsList();

            //if the followed user has moods, find his most recent mood and display it. If not,
            //only display his name
            if (followedUserMoods != null) {
                followedUserMoods = controller.sortMoodsByDate(followedUserMoods);
                originalMoodList.add(followedUserMoods.get(followedUserMoods.size() - 1));
            }
        }
        //make a copy of the following mood list for easy filtering.

        for (int i=0; i < originalMoodList.size(); i++ ){
            sortedFollowingMoods.add(originalMoodList.get(i));
        }
        adapter = new MoodAdapter(this, sortedFollowingMoods,controller.getCurrentUser().getName());
        //adapter = new ArrayAdapter<String>(this,
          //     R.layout.list_item, followedUserStringMessage);
        myFriendsListView.setAdapter(adapter);

        mapButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // send moods to the new activity
                Intent intent = new Intent(MyFriends.this ,MapActivity.class);
                intent.putExtra("activity", "MyFriends");
                if (triggerFilterButton.isActivated())
                    intent.getIntExtra("trigger", 1);

                if (emotionFilterButton.isActivated())
                    intent.putExtra("emotion", moodFilterSpinner.getSelectedItem().toString());

                if (weekFilterButton.isActivated())
                    intent.putExtra("lastweek", 2);

                startActivity(intent);
                finish();
            }

        });
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

    //Called when selecting a radio button.
    public void filter(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.emotionalStateFilterButton:
                if (checked) {
                    // Filter by emotional state
                    sortedFollowingMoods.clear();
                    adapter.clear();
                    sortedFollowingMoods = controller.filterByMood(moodFilterSpinner.getSelectedItem().toString(), originalMoodList);
                    adapter.addAll(sortedFollowingMoods);
                    adapter.notifyDataSetChanged();
                    myFriendsListView.setAdapter(adapter);
                    break;
                }
            case R.id.weekFilterButton:
                if (checked) {
                    // Filter by last week's moods only
                    sortedFollowingMoods.clear();
                    adapter.clear();
                    sortedFollowingMoods = controller.filterByWeek(originalMoodList);
                    adapter.addAll(sortedFollowingMoods);
                    adapter.notifyDataSetChanged();
                    myFriendsListView.setAdapter(adapter);
                    break;
                }
            case R.id.triggerFilterButton:
                if (checked) {
                    // Filter by Moods containing the trigger filter
                    sortedFollowingMoods.clear();
                    adapter.clear();
                    sortedFollowingMoods = controller.filterByTrigger(triggerText.getText().toString(), originalMoodList);
                    adapter.addAll(sortedFollowingMoods);
                    adapter.notifyDataSetChanged();
                    myFriendsListView.setAdapter(adapter);
                    break;
                }
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
