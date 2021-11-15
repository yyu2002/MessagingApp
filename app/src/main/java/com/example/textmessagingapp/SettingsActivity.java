package com.example.textmessagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button updateAccountSettings; // update account settings button
    private EditText userName, userStatus; // edit text fields username and status
    private CircleImageView userProfileImage; // user profile pic w/ circle image view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeFields(); // initializes class fields
    }

    private void initializeFields() {
        updateAccountSettings = (Button) findViewById(R.id.update_settings_button); // initialize update settings button
        userName = (EditText) findViewById(R.id.set_user_name); // initialize set username edit text
        userStatus = (EditText) findViewById(R.id.set_profile_status); // initialize set user status edit text
    }
}