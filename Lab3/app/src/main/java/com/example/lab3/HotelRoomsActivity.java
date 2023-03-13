// HotelRoomsActivity.java

package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HotelRoomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotelrooms);

        // Отримання компонентів інтерфейсу
        SeekBar seekBarNumberRoom = findViewById(R.id.seekBarNumberRoom);
        EditText editTextNumberRoom = findViewById(R.id.editTextNumberRoom);
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        Button buttonGuests = findViewById(R.id.buttonGuests);

        // Створення екземпляру класу Intent для другого екрану
        Intent intent = new Intent(this, GuestsActivity.class);

        // Налаштування для toggleButton
        // toggleButton у поточному застосунку використовується для того, аби сховати та показати кнопку постояльців
        toggleButton.setTextOn("Кнопка на екрані"); // Текст кнопки, коли вона увімкнена
        toggleButton.setTextOff("Кнопка схована"); // Текст кнопки, коли вона вимкнена
        toggleButton.setText("Кнопка схована"); // Початковий текст кнопки
        buttonGuests.setVisibility(View.INVISIBLE); // Так як спочатку кнопка toggleButton вимкнена, необхідно сховати кнопку постояльців

        // Обробник подій для компоненту seekBar
        seekBarNumberRoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int numberRoom, boolean b) {
                // Встановлюємо значення seekBar у текстове поле
                editTextNumberRoom.setText(String.valueOf(numberRoom));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                /* Спрацьовує на початку перетягування повзунка */
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* Спрацьовує по завершенню перетягування повзунка */
            }
        });

        // Обробник подій для toggleButton
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Якщо увімкнено, показуємо кнопку постояльців
                    buttonGuests.setVisibility(View.VISIBLE);
                } else {
                    // Інакше - ховаємо її
                    buttonGuests.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Обробник подій для кнопки пошуку
        // Для демонстрації працездатності кнопки відобразімо повідомлення, що наразі пошук знаходиться у розробці
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Створюємо та показуємо повідомлення з відповідним текстом
                Toast.makeText(getApplicationContext(), "Пошук за номером готелю у розробці :)", Toast.LENGTH_LONG).show();
            }
        });

        // Обробник подій для кнопки постояльців
        // Викликає другий екран
        buttonGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Викликаємо стандартний метод startActivity для обробки системою
                startActivity(intent);
            }
        });
    }
}