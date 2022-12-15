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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureLoginButton();
        configureRegisterButton();
        
        usernameField = findViewById(R.id.loginUsernameField);
        passwordField = findViewById(R.id.loginPasswordField);
    }

    private void configureRegisterButton() {
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewUserActivity.class));
            }
        });
    }

    private void configureLoginButton() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            login();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
    }

    private void login() {
        String request = new URLBuilder("login", true)
                .addParam("username", usernameField.getText().toString())
                .addParam("password", passwordField.getText().toString())
                .getURL();
        try {
            String response = RESTController.sendRequest(request, "POST");
            Log.d("CREATION", response);
            parseLoginResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLoginResponse(String response) {
        String[] responseList = response.split(",");
        boolean isAuthenticated = Boolean.parseBoolean(responseList[0]);
        int userIndex = Integer.parseInt(responseList[1]);
        String userType = responseList[2];
        if (isAuthenticated && userType.equals("driver")) {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("driverID", userIndex);
            startActivity(intent);
        }
    }

    private void start() {
        String driverBody = null;
        try {
            driverBody = RESTController.sendRequest("http://192.168.0.102:8080/drivers/1", "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (driverBody != null) {
            Log.d("CREATION", driverBody);
        } else {
            Log.d("CREATION", "NULL BODY");
        }
    }
}