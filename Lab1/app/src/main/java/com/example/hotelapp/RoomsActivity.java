// RoomsActivity.java

package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RoomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        // Отримання повідомлення з першого екрану
        // та зберігаємо його у змінній message
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Через вказівник на компонент екрану textView2 передаємо текст
        TextView textView2 = (TextView)findViewById(R.id.textView);
        textView2.setText(message);
    }
}