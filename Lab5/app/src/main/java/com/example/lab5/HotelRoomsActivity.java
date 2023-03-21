// HotelRoomsActivity.java

package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
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

        // створення анімації вильоту всього списку з-за екрану
        // translationX - зміщення за віссю X
        PropertyValuesHolder pvhTranslationX = PropertyValuesHolder.ofKeyframe(
            "translationX",
            Keyframe.ofFloat(0, -1000),
            Keyframe.ofFloat(1, 0)
        );
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(listView, pvhTranslationX);
        animator.setDuration(2000);
        animator.start();

        // створення анімації обертання
        // rotation - використовується для обертання, де значення можуть бути встановлені у градусах
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
            "rotation",
            Keyframe.ofFloat(0, 0),
            Keyframe.ofFloat(0.5f, 360),
            Keyframe.ofFloat(1, 0)
        );
        // додавання обробника подій при натисканні на елемент списку listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // необхідно викликати анімацію обертання у випадку, коли був обраний відповідний елемент у списку listView
                ObjectAnimator animatorRotation = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation);
                animatorRotation.setDuration(2000);
                animatorRotation.start();
            }
        });
    }
}