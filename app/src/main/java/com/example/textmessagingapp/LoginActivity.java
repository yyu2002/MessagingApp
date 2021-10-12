package com.example.textmessagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser; // current user
    private FirebaseAuth mAuth; // firebase authorization
    private ProgressDialog loadingBar; // loading bar

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

        mAuth = FirebaseAuth.getInstance(); // initialize firebase auth
        currentUser = mAuth.getCurrentUser(); // get current user

        initializeFields(); // initialize class fields

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        }); // when user clicks on link, send to register activity

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin() {
        String email = UserEmail.getText().toString(); // get text from email textfield
        String password = UserEmail.getText().toString(); // get text from password textfield

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } // if email field is empty, tell user

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } // if password field is empty, tell user

        else {
            loadingBar.setTitle("Signing in"); // display message while creating account
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true); // loading bar wont disappear till account created
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserToMainActivity();
                                Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss(); // dismiss loading bar if login successful
                            } // send user to main activity if sign in successful
                            else {
                                String message = task.getException().toString(); // get error message
                                Toast.makeText(LoginActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss(); // dismiss loading bar if error when logging in
                            } // if error occurs, tell user
                        }
                    }); // sign user in with email and pass
        }
    }

    private void initializeFields() {
        LoginButton = (Button) findViewById(R.id.login_button);
        PhoneLoginButton = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (EditText) findViewById(R.id.login_email);
        UserPassword = (EditText) findViewById(R.id.login_password);
        NeedNewAccountLink = (TextView) findViewById(R.id.need_new_account_link);
        ForgotPasswordLink = (TextView) findViewById(R.id.forgot_password_link);

        loadingBar = new ProgressDialog(this);
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
        startActivity(registerIntent); // send user to register activity if clickedcxcxçcvc
    }
}