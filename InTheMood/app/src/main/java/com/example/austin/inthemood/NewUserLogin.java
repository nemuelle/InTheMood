package com.example.austin.inthemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewUserLogin extends AppCompatActivity {

    /**
     *  To pass in a message to the next activity
     */
    public static final String EXTRA_MESSAGE = "com.example.inthemood.MESSAGE";
    public dataControler controller;

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private EditText mConfirmPWView;

    //Error message references.
    private TextView eU;
    private TextView eP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_login);

        // Initialize the data controller.
        controller = new dataControler();

        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPWView = (EditText) findViewById(R.id.confirm_password);

        // Initialize error messages and hide them by default.
        eU = (TextView) findViewById(R.id.eUser);
        eU.setVisibility(View.GONE);
        eP = (TextView) findViewById(R.id.ePass);
        eP.setVisibility(View.GONE);
    }

    /**
     * Called when the register button is clicked.
     *
     * @param view
     */
    public void register(View view) {
        if (validRegistration() == 1) {
            eU = (TextView) findViewById(R.id.eUser);
            eU.setVisibility(View.GONE);
            eP = (TextView) findViewById(R.id.ePass);
            eP.setVisibility(View.GONE);
            Intent intent = new Intent(this, MainUser.class);
            String name = mUserView.getText().toString();
            String password = mPasswordView.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, name);

            User newUser = new User(name, password);
            controller.addToUserList(newUser);

            startActivity(intent);
        } else if (validRegistration() == -1) {
            eU.setVisibility(View.VISIBLE);
            eU.setText(getString(R.string.eUser1));
        } else if (validRegistration() == -2) {
            eU.setVisibility(View.VISIBLE);
            eU.setText(getString(R.string.eUser2));
        } else if (validRegistration() == -4) {
            eP.setVisibility(View.VISIBLE);
            eP.setText(getString(R.string.ePass1));
        } else if (validRegistration() == -8) {
            eP.setVisibility(View.VISIBLE);
            eP.setText(getString(R.string.ePass2));
        }
    }

    /**
     * This function checks if the username and password supplied by the User are valid for
     * registering a new account. A valid username is any unique username (can't already be
     * registered in the database) that is not null, while a valid password is a nonempty password
     *
     * @return -1 if username is empty, -2 if username is already taken, -4 if password is empty,
     * -8 if the password and confirm fields do not match up, 1 if registration is valid.
     */
    public int validRegistration() {
        if (mUserView.getText().toString().equals("")) {
            return -1;
        }
        else if (controller.searchForUserbyName(mUserView.getText().toString()) != null) {
            return -2;
        } else if (mPasswordView.getText().toString().equals("")) {
            return -4;
        } else if (!mPasswordView.getText().toString().equals(mConfirmPWView.getText().toString())){
            return -8;
        }
        return 1;
    }
}
