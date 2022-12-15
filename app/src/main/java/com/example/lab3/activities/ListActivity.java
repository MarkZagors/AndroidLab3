package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lab3.R;

public class ListActivity extends AppCompatActivity {

    private int driverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        configureUserSettingsButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            driverID = extras.getInt("driverID");
        }
    }

    private void configureUserSettingsButton() {
        Button loginButton = findViewById(R.id.userButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, UserActivity.class);
                intent.putExtra("driverID", driverID);
                startActivity(intent);
            }
        });
    }
}