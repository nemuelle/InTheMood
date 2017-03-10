package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewUserLogin extends AppCompatActivity {

    /**
     *  To pass in a message to the next activity
     */
    public static final String EXTRA_MESSAGE = "com.example.inthemood.MESSAGE";

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_login);

        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);

    }

    /**
     * Called when the register button is clicked.
     *
     * @param view
     */
    public void register(View view) {
        Intent intent = new Intent(this, MainUser.class);
        EditText editText = (EditText) findViewById(R.id.user);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
