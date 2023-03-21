// MainActivity.java

package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

        // отримання значення вказівника EditText з id EditTextCount
        EditText EditTextCount = (EditText)findViewById(R.id.EditTextCount);

        // створення анімації плавного "падіння"
        // translationY - зміщення за віссю Y
        PropertyValuesHolder pvhTranslationY = PropertyValuesHolder.ofKeyframe(
            "translationY",
            Keyframe.ofFloat(0, -500),
            Keyframe.ofFloat(0.5f, 0)
        );
        ObjectAnimator animatorEditText = ObjectAnimator.ofPropertyValuesHolder(EditTextCount, pvhTranslationY);
        animatorEditText.setDuration(3000);
        animatorEditText.start();

        // отримання кнопки генерації
        Button buttonGenerate = (Button)findViewById(R.id.ButtonGenerate);

        // створення анімації блимання кнопки
        // alpha - зміна прозорості у відсотках
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofKeyframe(
        "alpha",
            Keyframe.ofFloat(0, 1),
            Keyframe.ofFloat(0.5f, 0.3f),
            Keyframe.ofFloat(1, 1)
        );
        ObjectAnimator animatorButton = ObjectAnimator.ofPropertyValuesHolder(buttonGenerate, pvhAlpha);
        animatorButton.setDuration(2000);
        animatorButton.setRepeatCount(ObjectAnimator.INFINITE); // вказуємо, що анімація повинна повторюватись завжди
        animatorButton.start();

        // встановлюємо обробник подій при натисканні
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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