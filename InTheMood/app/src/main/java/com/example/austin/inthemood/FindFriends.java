package com.example.austin.inthemood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FindFriends extends AppCompatActivity {

    //UI Elements
    private EditText emailSearchText;
    private Button searchButton;
    private Button followButton;
    private TextView searchResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        //Initialize UI Elements
        emailSearchText = (EditText) findViewById(R.id.emailSearchEditText);
        searchButton = (Button) findViewById(R.id.searchButton);
        followButton = (Button) findViewById(R.id.followButton);
        searchResult = (TextView) findViewById(R.id.searchResultTextView);
    }
}
