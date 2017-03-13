package com.example.austin.inthemood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class MyFriends extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    public dataControler controller;
    private ListView myFriendsListView;
    private ArrayList<User> followingList;
    private ArrayList<String> followingNamesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        loadFromFile();
        myFriendsListView = (ListView) findViewById(R.id.myFriendsListView);
        followingList = controller.getCurrentUser().getMyFollowingList();
        followingNamesList = new ArrayList<String>();
        for (int i = 0; i < followingList.size(); i++){
            followingNamesList.add(followingList.get(i).getName());
        }
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, followingNamesList);
        myFriendsListView.setAdapter(adapter);

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
