package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainUser extends AppCompatActivity {

    private dataControler controller;
    public static final String EXTRA_MESSAGE = "com.example.inthemood.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
    }

    public void myFriendsMoods(View view){
        Intent intent = new Intent(this, MyFriendsMoods.class);
        startActivity(intent);
    }

    public void myMoods(View view){
        Intent intent = new Intent(this, MyMoods.class);
        startActivity(intent);
    }

    public void myFriends(View view){
        Intent intent = new Intent(this, MyFriends.class);
        startActivity(intent);
    }

    public void signOut(View view){
        controller.setCurrentUser(null);
        Intent intent = new Intent(this, ExistingUserLogin.class);
    }

}
