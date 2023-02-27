// MainActivity.java

package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final String EXTRA_MESSAGE = "MainActivity.MESSAGE";
    public void sendMessage(View view) {
        // Створення екземпляру класу Intent
        Intent intent = new Intent(this, RoomsActivity.class);

        // Отримання значення вказівника кнопки з id button
        Button button = (Button)findViewById(R.id.button);
        // Отримання тексту кнопки
        String message = button.getText().toString();

        // Додаємо значення змінної message у створений екземпляр класу Intent
        intent.putExtra(EXTRA_MESSAGE, message);
        // Викликаємо стандартний метод startActivity для обробки системою
        startActivity(intent);
    }
}