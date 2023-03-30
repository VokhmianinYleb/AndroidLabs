// GetRowsTasks.java

package com.example.lab6;

import android.database.Cursor;
import android.os.AsyncTask;
import java.util.ArrayList;

/**
 * Клас, який завантажує дані з бази даних у фоновому потоці, аби не блокувати основний потік
 * та не перешкоджати роботі інтерфейсу
 */
class GetRowsTask extends AsyncTask<Object, Object, Cursor> {
    private DatabaseConnector databaseConnector;
    private MainActivity mainActivity;

    // Конструктор
    GetRowsTask(MainActivity activity) {
        this.mainActivity = activity;
        databaseConnector = new DatabaseConnector(activity);
    }

    /**
     * Повертає усі дані з бази даних
     * @param objects об'єкти
     * @return отримані дані з бази даних
     */
    @Override
    protected Cursor doInBackground(Object... objects) {
        databaseConnector.open();
        return databaseConnector.getTableAllRows();
    }

    /**
     * Оновлює ListView актуальними даними з бази даних
     * @param cursor об'єкт курсору
     */
    @Override
    protected void onPostExecute(Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<>(); // масив рядків для виведення на екран у ListView
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrayList.add(
                String.format(
                    "№%s " +
                    "%s " +
                    "(%s)",
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
            );
        }

        databaseConnector.close(); // закриття підключення до бази даних
        this.mainActivity.update_list(arrayList);
    }
}