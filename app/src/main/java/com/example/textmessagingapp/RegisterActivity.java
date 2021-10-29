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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton; // create account button
    private EditText UserEmail; // email field
    private EditText UserPassword; // password field
    private TextView AlreadyHaveAccountLink; // already have account link

    private FirebaseAuth mAuth; // firebase authentication

    private ProgressDialog loadingBar; // loading bar when creating account

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initializeFields(); // initialize class fields

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        }); // when user clicks on link, send to login activity

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        }); // when user clicks on create account button
    }

    private void createNewAccount() {
        String email = UserEmail.getText().toString(); // get text from email textfield
        String password = UserEmail.getText().toString(); // get text from password textfield

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        } // if email field is empty, tell user

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } // if password field is empty, tell user

        else {
            loadingBar.setTitle("Creating account"); // display message while creating account
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true); // loading bar wont disappear till account created
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserToMainActivity(); // send user to main page if account created successfully
                                Toast.makeText(RegisterActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss(); // dismiss loading bar if account created successfully
                            } // if account created successfully, tell user
                            else {
                                String message = task.getException().toString(); // get error message
                                Toast.makeText(RegisterActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss(); // dismiss loading bar if error occurs
                            } // if error occurs, tell user
                        }
                    });
        } // create user
    }

    private void initializeFields() {
        CreateAccountButton = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_account_link);

        loadingBar = new ProgressDialog(this);
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent); // send user to login activity when clicked
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK); // user cannot go back if they press back button, must use logout button
        startActivity(mainIntent); // send user to main activity when clicked
        finish(); // close activity
    }
}