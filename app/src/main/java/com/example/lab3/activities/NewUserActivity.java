package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab3.R;
import com.example.lab3.RESTController;
import com.example.lab3.builders.URLBuilder;

import java.io.IOException;

public class NewUserActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private EditText emailField;
    private EditText dateOfBirthField;
    private EditText telephoneNumberField;
    private EditText driverLicenceIDField;
    private EditText medicalCheckIDField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        configureCreateButton();

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        emailField = findViewById(R.id.emailField);
        dateOfBirthField = findViewById(R.id.dateOfBirthField);
        telephoneNumberField = findViewById(R.id.phoneNumberField);
        driverLicenceIDField = findViewById(R.id.driverlicenceIDField);
        medicalCheckIDField = findViewById(R.id.medicalCheckIDField);
    }

    private void configureCreateButton() {
        Button createButton = findViewById(R.id.saveButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCreate();
            }
        });
    }

    private void clickCreate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createDriver();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void createDriver() {
        String request = new URLBuilder("drivers/add", true)
                .addParam("username", usernameField.getText().toString())
                .addParam("password", passwordField.getText().toString())
                .addParam("dateOfBirth", dateOfBirthField.getText().toString())
                .addParam("email", emailField.getText().toString())
                .addParam("telephoneNumber", telephoneNumberField.getText().toString())
                .addParam("driverLicenceID", driverLicenceIDField.getText().toString())
                .addParam("medicalCheckID", medicalCheckIDField.getText().toString())
                .getURL();
        try {
            String response = RESTController.sendRequest(request, "POST");
            Log.d("CREATION", response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(NewUserActivity.this, MainActivity.class));
    }
}