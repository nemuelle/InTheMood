package com.example.austin.inthemood;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;


/**
 * A login screen that offers login via username/password.
 */
public class ExistingUserLogin extends AppCompatActivity{
    /**
     *  To pass in a message to the next activity
     */
    public static final String EXTRA_MESSAGE = "com.example.inthemood.MESSAGE";
    private static final String FILENAME = "file.sav";
    public dataControler controller;

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user_login);


        // Initialize the data controller.
        loadFromFile();


        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            Type objectType = new TypeToken<dataControler>() {}.getType();
            controller = gson.fromJson(in, objectType);
        } catch (FileNotFoundException e) {
            User firstUser = new User("?????????????", "?????????????");
            controller = new dataControler(firstUser);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Called when the log in button is clicked.
     *
     * @param view
     */
    public void login(View view) {
        Intent intent = new Intent(this, MainUser.class);
        EditText editText = (EditText) findViewById(R.id.user);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, NewUserLogin.class);
        startActivity(intent);
    }
}

