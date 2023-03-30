// PaintActivity.java

package com.example.lab7;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PaintActivity extends AppCompatActivity {
    private static final int MENU_SELECT_COLOR = Menu.FIRST + 1;
    private static final int MENU_CLEAR_IMAGE = Menu.FIRST + 2;
    private static final int MENU_SAVE_IMAGE = Menu.FIRST + 3;
    private static final int MENU_LAST_SAVE_INFO = Menu.FIRST + 4;

    private PaintView paintView;

    private int defaultColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Створюємо об'єкт класу PaintView
        this.paintView = new PaintView(this);
        setContentView(this.paintView);

        // Відображаємо інформацію про останнє збережене зображення
        this.showPath();
    }

    /**
     * Створює меню застосунку
     * @param menu об'єкт меню
     * @return значення типу Boolean
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_SELECT_COLOR, Menu.NONE, R.string.menu_select_color);
        menu.add(Menu.NONE, MENU_CLEAR_IMAGE, Menu.NONE, R.string.menu_clear_image);
        menu.add(Menu.NONE, MENU_SAVE_IMAGE, Menu.NONE, R.string.menu_save_image);
        menu.add(Menu.NONE, MENU_LAST_SAVE_INFO, Menu.NONE, R.string.menu_last_save_info);

        return true;
    }

    /**
     * Відслідковує натискання пунктів меню
     * @param item елемент меню
     * @return значення типу Boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Перебираємо пункти меню порівнюючи їх з ідентифікатором обраного елементу меню
        switch (item.getItemId()) {
            // Обрання кольору
            case MENU_SELECT_COLOR: {
                // Створюємо діалогове вікно, використовуючи клас AmbilWarnaDialog
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, this.defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    /**
                     * Викликається при натисканні кнопки ОК
                     * @param dialog об'єкт класу AmbilWarnaDialog
                     * @param color обраний колір користувачем
                     */
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        defaultColor = color; // встановлення кольору за замовчуванням
                        paintView.set_line_color(color); // зміна кольору лінії
                    }

                    /**
                     * Викликається при натисканні кнопки Скасування
                     * @param dialog об'єкт класу AmbilWarnaDialog
                     */
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }
                });

                dialog.show(); // відображення діалогового вікна

                return true;
            }
            // Очищення полотна
            case MENU_CLEAR_IMAGE:
                paintView.clear();
                return true;
            // Збереження зображення
            case MENU_SAVE_IMAGE: {
                String path = paintView.save_image();
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                savePath(path);
                return true;
            }
            // Отримання інформації щодо останнього збереженого зображення
            case MENU_LAST_SAVE_INFO: {
                this.showPath();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Зберігає повний шлях зображення, який можна буде отримати між сеансами
     * @param path повний шлях
     */
    public void savePath(String path) {
        // Збереження повного шляху збереженого зображення у налаштуваннях застосунку
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_image_path_key), path);
        editor.apply();
    }

    /**
     * Виводить на екран повний шлях останнього збереженого зображення, якщо він є,
     * або інформацію про те, що його немає, якщо його немає
     */
    public void showPath() {
        // Отримання повного шляху збереженого зображення з налаштувань застосунку
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String imagePath = sharedPreferences.getString(getString(R.string.saved_image_path_key), "");

        // Якщо шлях було знайдено, відображаємо його на екрані
        if (!imagePath.isEmpty()) {
            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
        }
        else { // інакше - інформацію про те, що шлях не був знайдений
            Toast.makeText(this, "Повний шлях останнього зображення не знайдено!", Toast.LENGTH_LONG).show();
        }
    }
}