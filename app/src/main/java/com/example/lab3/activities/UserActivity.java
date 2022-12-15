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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserActivity extends AppCompatActivity {

    private int driverID;
    private EditText usernameField;
    private EditText passwordField;
    private EditText emailField;
    private EditText phoneField;
    private EditText dateOfBirthField;
    private EditText driverLicenceField;
    private EditText medicalCheckField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            driverID = extras.getInt("driverID");
        }

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        emailField = findViewById(R.id.emailField);
        phoneField = findViewById(R.id.phoneNumberField);
        dateOfBirthField = findViewById(R.id.dateOfBirthField);
        driverLicenceField = findViewById(R.id.driverlicenceIDField);
        medicalCheckField = findViewById(R.id.medicalCheckIDField);

        configureFieldsWithThread();
        configureSaveButtonWithThread();
        configureDeleteButtonWithThread();
    }

    private void configureFieldsWithThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getResponseAndConfigureFields();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void configureSaveButtonWithThread() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveUser();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void configureDeleteButtonWithThread() {
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            deleteUser();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void getResponseAndConfigureFields() {
        String url = new URLBuilder("drivers/" + String.valueOf(driverID), false).getURL();
        try {
            String response = RESTController.sendRequest(url, "GET");
            configureFields(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureFields(String response) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String username = jsonObject.getString("username");
                    usernameField.setText(jsonObject.getString("username"));
                    passwordField.setText(jsonObject.getString("password"));
                    emailField.setText(jsonObject.getString("email"));
                    dateOfBirthField.setText(jsonObject.getString("dateOfBirth"));
                    phoneField.setText(jsonObject.getString("telephoneNumber"));
                    driverLicenceField.setText(jsonObject.getString("driverLicenceID"));
                    medicalCheckField.setText(jsonObject.getString("medicalCheckID"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveUser() {
        String request = new URLBuilder("drivers/" + String.valueOf(driverID), true)
                .addParam("username", usernameField.getText().toString())
                .addParam("password", passwordField.getText().toString())
                .addParam("dateOfBirth", dateOfBirthField.getText().toString())
                .addParam("email", emailField.getText().toString())
                .addParam("telephoneNumber", phoneField.getText().toString())
                .addParam("driverLicenceID", driverLicenceField.getText().toString())
                .addParam("medicalCheckID", medicalCheckField.getText().toString())
                .getURL();
        try {
            String response = RESTController.sendRequest(request, "PUT");
            Log.d("CREATION", response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(UserActivity.this, ListActivity.class));
    }

    private void deleteUser() {
        String request = new URLBuilder("drivers/" + String.valueOf(driverID), false).getURL();
        try {
            String response = RESTController.sendRequest(request, "DELETE");
        } catch (IOException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(UserActivity.this, MainActivity.class));
    }

}