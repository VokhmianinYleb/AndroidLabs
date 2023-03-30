// PaintView.java

package com.example.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Клас PaintView, що є наслідником класу View
 * Зберігає об'єкти графічних класів, таких як Bitmap, Canvas, Paint тощо
 * Має методи очищення полотна,
 * встановлення та отримання кольору пензля,
 * встановлення та отримання товщини пензля,
 * збережння зображення,
 * відслідковування та обробки подій при натисканні, зміщенні курсору
 */
public class PaintView extends View {
    private static final float paint_tolerance = 10;
    private Bitmap bmp;

    private Canvas bmp_canvas;
    private final Paint paint_for_screen;
    private final Paint paint_for_line;
    private final HashMap<Integer, Path> map_pid_path;
    private final HashMap<Integer, Point> map_pid_point_prev;

    // Конструктор
    public PaintView(Context context){
        super(context);
        this.paint_for_screen = new Paint();
        this.paint_for_line = new Paint();
        this.paint_for_line.setColor(Color.BLACK);

        this.paint_for_line.setAntiAlias(true);
        this.paint_for_line.setStrokeWidth(6);
        this.paint_for_line.setStyle(Paint.Style.STROKE);
        this.paint_for_line.setStrokeCap(Paint.Cap.ROUND);
        this.map_pid_path = new HashMap<Integer, Path>();
        this.map_pid_point_prev = new HashMap<Integer, Point>();
    }

    /**
     * Створює новий рисунок та об'єкт Canvas для нього, якщо розміри екрану були змінені
     * @param w ширина
     * @param h висота
     * @param oldW попередня ширина
     * @param oldH попередня висота
     */
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bmp_canvas = new Canvas(bmp);
        bmp.eraseColor(Color.WHITE);
    }

    /**
     * Очищує полотно
     */
    void clear() {
        map_pid_path.clear(); // видалення ліній
        map_pid_point_prev.clear(); // видалення точок
        bmp.eraseColor(Color.WHITE); // очищення рисунка
        invalidate(); // оновлення екрану
    }

    /**
     * Встановлює новий колір лінії
     * @param color новий колір
     */
    public void set_line_color(int color) {
        paint_for_line.setColor(color);
    }

    /**
     * Повертає поточний колір лінії
     * @return поточний колір лінії
     */
    public int get_line_color() {
        return paint_for_line.getColor();
    }

    /**
     * Встановлює товщину лінії
     * @param width нова товщина лінії
     */
    public void set_line_width(int width) {
        paint_for_line.setStrokeWidth(width);
    }

    /**
     * Повертає поточну товщину лінії
     * @return поточна товщина лінії
     */
    public int get_line_width() {
        return (int)paint_for_line.getStrokeWidth();
    }

    /**
     * Викликається для відображення компонента
     * @param canvas об'єкт типу Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Відображення фону
        canvas.drawBitmap(bmp, 0,0, paint_for_screen);

        // Відображення лінії
        for (Integer key : map_pid_path.keySet())
            canvas.drawPath(map_pid_path.get(key), paint_for_line);
    }

    /**
     * Викликається при дотику до полотна
     * @param event подія
     * @return значення типу Boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // одержання дії
        int actionIndex = event.getActionIndex(); // одержання індексу дії

        // Відслідковування початку дотику
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex),
                    event.getPointerId(actionIndex));
        }
        else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            // Кінець дотику
            touchEnded(event.getPointerId(actionIndex));
        }
        else {
            // Переміщення вказівника по екрану
            touchMoved(event);
        }
        invalidate(); // оновлення екрану

        return true;
    }

    /**
     * Відслідковує початок дотику
     * @param x координата x
     * @param y координата y
     * @param lineID ідентифікатор лінії
     */
    private void touchStarted(float x, float y, int lineID) {
        Path path; // лінія
        Point point; // остання точка

        // Якщо вже є лінія для даного ідентифікатору
        if (map_pid_path.containsKey(lineID)) {
            path = map_pid_path.get(lineID);
            path.reset(); // оновлення лінії
            point = map_pid_point_prev.get(lineID); // отримання останньої точки
        }
        else {
            path = new Path(); // створення нової лінії
            map_pid_path.put(lineID, path); // додавання лінії
            point = new Point(); // створення нової точки
            map_pid_point_prev.put(lineID, point); // додавання нової точки
        }

        // оновлення координат
        path.moveTo(x, y); point.x = (int)x; point.y = (int)y;
    }

    /**
     * Відслідковує переміщення вказівника
     * @param event подія
     */
    private void touchMoved(MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            int pid = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pid);

            if (map_pid_path.containsKey(pid)) {
                // Отримання нових координат
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                // Отримання лінії та останньої точки
                Path path = map_pid_path.get(pid);
                Point point = map_pid_point_prev.get(pid);

                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                // Якщо відстань є достатньою
                if (deltaX >= paint_tolerance || deltaY >= paint_tolerance) {
                    // Подовження лінії до нової точки
                    path.quadTo(point.x, point.y, (newX + point.x) / 2, (newY + point.y) / 2);

                    // Нові координати
                    point.x = (int)newX;
                    point.y = (int)newY;
                }
            }
        }
    }

    /**
     * Відслідковує кінець дотику
     * @param lineID ідентифікатор лінії
     */
    private void touchEnded(int lineID) {
        Path path = map_pid_path.get(lineID); // лінія
        bmp_canvas.drawPath(path, paint_for_line); // малювання лінії
        path.reset(); // очищення лінії від точок
    }

    /**
     * Зберігає зображення та повертає повний шлях до нього (за завданням)
     * @return повний шлях до збереженого зображення
     */
    public String save_image() {
        // Ім'я файлу
        String fileName = "painting" + (System.currentTimeMillis() / 1000);
        // Заповнення необхідними метаданими
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED,System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/PNG");

        // Одержання шляху для збереження рисунків
        Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream outStream = getContext().getContentResolver().openOutputStream(uri);
            // Кодування рисунку у формат
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            // Передача накопичених даних потоку і його закриття
            outStream.flush();
            outStream.close();
        }
        catch (IOException ex) {
            // Повідомлення про помилку збереження
            Toast.makeText(getContext(), "error! "+ ex,Toast.LENGTH_SHORT).show();
        }

        // Отримання повного шляху збереженого зображення
        String[] proj = {MediaStore.Images.Media.DATA};
        // Отримання курсору пошуку
        Cursor cursor = getContext().getContentResolver().query(uri, proj, null, null, null);

        // Повертаємо шлях з uri, якщо курсор не знайдено
        if (cursor == null) {
            return uri.getPath();
        }
        else { // інакше повертаєммо повний шлях зображення
            cursor.moveToFirst(); // перший знайдений
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            cursor.close();

            return path;
        }
    }
}