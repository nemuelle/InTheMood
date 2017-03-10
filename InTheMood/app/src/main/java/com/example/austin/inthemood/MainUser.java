package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ExistingUserLogin.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message + " is now logged in");

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main_user);
        layout.addView(textView);
    }
}
