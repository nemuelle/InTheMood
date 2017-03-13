package com.example.austin.inthemood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MyFriends extends AppCompatActivity {

    //UI Elements
    private ListView friendsListView;
    private Button findFriendsButton;

    private dataControler controller;
    private ArrayList<User> myFollowingList;
    private ArrayList<String> myFollowingNamesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        //Initialize UI Elements
        friendsListView = (ListView) findViewById(R.id.myFriendsListView);
        findFriendsButton = (Button) findViewById(R.id.findFriendsButton);

        ArrayList<User> myFollowingList = controller.getCurrentUser().getMyFollowingList();
        ArrayList<String> myFollowingNamesList = new ArrayList<String>();
        for (int i = 0; i < myFollowingList.size(); i++){
            myFollowingNamesList.add(myFollowingList.get(i).getName());
        }

        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, myFollowingNamesList);
        friendsListView.setAdapter(adapter);
    }
}
