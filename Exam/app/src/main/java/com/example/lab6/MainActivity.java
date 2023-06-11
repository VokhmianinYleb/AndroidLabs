// MainActivity.java

package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPlaces = findViewById(R.id.buttonPlaces);
        buttonPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Places.class);
                startActivity(intent);
            }
        });

        Button buttonTickets = findViewById(R.id.buttonTickets);
        buttonTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tickets.class);
                startActivity(intent);
            }
        });
    }
}