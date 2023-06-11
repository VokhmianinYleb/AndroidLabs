package com.example.lab6;

import android.database.Cursor;
import android.os.AsyncTask;
import java.util.ArrayList;

class GetRowsTickets extends AsyncTask<Object, Object, Cursor> {
    private DatabaseConnector databaseConnector;
    private Tickets mainActivity;

    // Конструктор
    GetRowsTickets(Tickets activity) {
        this.mainActivity = activity;
        databaseConnector = new DatabaseConnector(activity);
    }

    @Override
    protected Cursor doInBackground(Object... objects) {
        databaseConnector.open();
        return databaseConnector.getTableAllTickets();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<>(); // масив рядків для виведення на екран у ListView
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrayList.add(
                    String.format(
                            "№%s " +
                                    "%s " +
                                    "(%s)" +
                            " %s",
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            );
        }

        databaseConnector.close(); // закриття підключення до бази даних
        this.mainActivity.update_list(arrayList);
    }
}