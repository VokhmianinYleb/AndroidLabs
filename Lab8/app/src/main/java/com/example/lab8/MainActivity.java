// MainActivity.java

package com.example.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.opengl.GLES20;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "OpenGLES20Activity";
    private MyGLSurfaceView mGLView;

    private static final int MENU_SELECT_COLOR_ONE = Menu.FIRST + 1;
    private static final int MENU_SELECT_COLOR_TWO = Menu.FIRST + 2;
    private static final int MENU_SELECT_COLOR_THREE = Menu.FIRST + 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Створюємо і підключаємо компонент
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause(); // Зупиняємо потік малювання
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume(); // Старт потоку малювання
    }

    /**
     * Метод для компіляції OpenGL шейдеру
     * @param type - тип шейдера
     * @param shaderCode - текст шейдера
     * @return - повертає id для шейдера
     */
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    /**
     * Метод відладки OpenGL викликів
     * @param glOperation - назва OpenGL виклику для перевірки
     */
    public static void checkGlError(String glOperation) {
        int error; // одержання усіх помилок у циклі
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            // створення виключення
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Створює меню застосунку
     * @param menu об'єкт меню
     * @return значення типу Boolean
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_SELECT_COLOR_ONE, Menu.NONE, R.string.menu_select_color_one);
        menu.add(Menu.NONE, MENU_SELECT_COLOR_TWO, Menu.NONE, R.string.menu_select_color_two);
        menu.add(Menu.NONE, MENU_SELECT_COLOR_THREE, Menu.NONE, R.string.menu_select_color_three);

        return true;
    }

    /**
     * Відслідковує натискання пунктів меню
     * @param item елемент меню
     * @return значення типу Boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Створюємо діалогове вікно, використовуючи клас AmbilWarnaDialog
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, Color.BLACK, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            /**
             * Викликається при натисканні кнопки ОК
             * @param dialog об'єкт класу AmbilWarnaDialog
             * @param color обраний колір користувачем
             */
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                MyGLRenderer renderer = mGLView.getRenderer();
                Figure figure;
                switch(item.getItemId()) {
                    case MENU_SELECT_COLOR_ONE:
                        figure = renderer.getTriangle();
                        break;
                    case MENU_SELECT_COLOR_TWO:
                        figure = renderer.getSquare();
                        break;
                    case MENU_SELECT_COLOR_THREE:
                        figure = renderer.getHexagon();
                        break;
                    default:
                        return;
                }
                figure.setColor(color);
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

        return super.onOptionsItemSelected(item);
    }
}