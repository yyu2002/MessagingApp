package com.example.textmessagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser; // current user
    private Button LoginButton; // login button
    private Button PhoneLoginButton; // login with phone code button
    private EditText UserEmail; // email field
    private EditText UserPassword; // password field
    private TextView NeedNewAccountLink; // need new account link
    private TextView ForgotPasswordLink; // forgot password link


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields(); // initialize class fields

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        }); // when user clicks on link, send to register activity
    }

    private void InitializeFields() {
        LoginButton = (Button) findViewById(R.id.login_button);
        PhoneLoginButton = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (EditText) findViewById(R.id.login_email);
        UserPassword = (EditText) findViewById(R.id.login_password);
        NeedNewAccountLink = (TextView) findViewById(R.id.need_new_account_link);
        ForgotPasswordLink = (TextView) findViewById(R.id.forgot_password_link);

    }

    @Override
    protected void onStart() {  // check if user is already logged in;
        super.onStart();        // if yes, send user to main activity

        if (currentUser != null) { // authenticated
            // send user to main activity if already logged in
            sendUserToMainActivity();
        }
    }

    private void sendUserToMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent); // send user to main activity if logged in already
    }

    private void sendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent); // send user to main activity if logged in already
    }
}