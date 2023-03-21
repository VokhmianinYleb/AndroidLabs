// MainActivity.java

package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // отримання кнопки генерації
        Button buttonGenerate = (Button)findViewById(R.id.ButtonGenerate);
        // встановлюємо обробник подій при натисканні
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // отримання значення вказівника EditText з id EditTextCount
                EditText EditTextCount = (EditText)findViewById(R.id.EditTextCount);

                // отримання числового значення з текстового поля для введення
                int count = Integer.parseInt(EditTextCount.getText().toString());

                // Створення екземпляру класу Intent
                Intent intent = new Intent(v.getContext(), HotelRoomsActivity.class);

                // Додаємо значення змінної count у створений екземпляр класу Intent
                intent.putExtra("count", count);

                // Викликаємо стандартний метод startActivity для обробки системою
                startActivity(intent);
            }
        });
    }
}