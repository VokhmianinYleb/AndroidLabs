// HotelRoomsActivity.java

package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HotelRoomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotelrooms);

        // Отримання кількості з першого екрану
        // збереження його у змінній count
        Intent intent = getIntent();
        int count = intent.getIntExtra("count", 0);

        // створення масиву об'єктів класу SpannableString
        SpannableString[] array = new SpannableString[count];

        // заповнення масиву текстом у циклі
        for (int i = 0; i < count; i++) {
            // створюємо змінну з номером готелю
            String room = "Кімната " + (i + 1);
            // створюємо змінну з номером гостя
            String guest = "Гість " + (i + 1);

            // додаємо конкатеновані рядки у масив
            array[i] = new SpannableString(room + " " + guest);
            // встановлюємо чорний колір для символів з 0 по довжині рядку кімнати
            // так як стандартним кольором є чорний, цей рядок не є обов'язковим, але ми все одно явно вкажемо
            array[i].setSpan(new ForegroundColorSpan(Color.BLACK), 0, room.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // встановлюємо синій колір для символів, починаючи з довжини рядка кімнати + 1, закінчуючи довжиною усього рядку + 1
            array[i].setSpan(new ForegroundColorSpan(Color.BLUE), room.length() + 1, room.length() + guest.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // одержання адаптера для співставлення рядка тексту з компонентом інтерфейсу, який буде відображати текст на екрані
        ArrayAdapter<SpannableString> adapter = new ArrayAdapter<SpannableString>(this, android.R.layout.simple_list_item_1, array);

        // одержання посилання на екземпляр ListView
        ListView listView = (ListView)findViewById(R.id.ListHotelRooms);

        // встановлення створеного адаптера
        listView.setAdapter(adapter);
    }
}