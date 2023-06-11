package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseConnector {
    private static final String DATABASE_NAME = "Cinema7.db";
    public SQLiteDatabase database;
    private DBHelper databaseOpenHelper;

    // Конструктор
    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DBHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public Cursor getTableRowByID(Integer idForEdit) {
        return database.query(
            DBHelper.TABLE,
            new String[] {
                DBHelper.TABLE_COLUMN_PLACE_ID,
                DBHelper.TABLE_COLUMN_ROW,
                DBHelper.TABLE_COLUMN_VIEW
            },
            "_id=?",
            new String[] {String.valueOf(idForEdit)},
            null,
            null,
            null
        );
    }

    public Cursor getTableRowByIDTicket(Integer idForEdit) {
        return database.query(
                DBHelper.TABLE_TICKETS,
                new String[] {
                        DBHelper.TABLE_COLUMN_TICKET_ID,
                        DBHelper.TABLE_COLUMN_FILM,
                        DBHelper.TABLE_COLUMN_LOCATION,
                        DBHelper.TABLE_COLUMN_DATE
                },
                "_id=?",
                new String[] {String.valueOf(idForEdit)},
                null,
                null,
                null
        );
    }

    public Cursor getTableAllRows() { // одержання курсору для доступу до результатів запиту
        return database.query(
            DBHelper.TABLE,
            new String[] {
                DBHelper.TABLE_COLUMN_PLACE_ID,
                DBHelper.TABLE_COLUMN_ROW,
                DBHelper.TABLE_COLUMN_VIEW
            },
            null,
            null,
            null,
            null,
            DBHelper.TABLE_COLUMN_PLACE_ID
        );
    }

    public Cursor getTableAllTickets() {
        return database.query(
                DBHelper.TABLE_TICKETS,
                new String[] {
                        DBHelper.TABLE_COLUMN_TICKET_ID,
                        DBHelper.TABLE_COLUMN_FILM,
                        DBHelper.TABLE_COLUMN_LOCATION,
                        DBHelper.TABLE_COLUMN_DATE
                },
                null,
                null,
                null,
                null,
                DBHelper.TABLE_COLUMN_TICKET_ID
        );
    }

    public void insertRow(Integer _id, Integer row_, String view_) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_COLUMN_PLACE_ID, _id);
        contentValues.put(DBHelper.TABLE_COLUMN_ROW, row_);
        contentValues.put(DBHelper.TABLE_COLUMN_VIEW, view_);
        open();
        database.insert(DBHelper.TABLE, null, contentValues);
        close();
    }

    public void editTableRow(Integer _id, Integer newID, Integer row_, String view_) {
        database = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_COLUMN_PLACE_ID, newID);
        contentValues.put(DBHelper.TABLE_COLUMN_ROW, row_);
        contentValues.put(DBHelper.TABLE_COLUMN_VIEW, view_);

        String[] whereArgs = new String[]{String.valueOf(_id)};

        database.update(DBHelper.TABLE, contentValues, "_id=?", whereArgs);
    }

    public void deleteTableRowTicket(Integer _id) {
        open();
        database.delete(DBHelper.TABLE_TICKETS, "_id=" + _id, null);
        close();
    }

    public void insertRowTicket(String film_, Integer place_, String date_) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_COLUMN_FILM, film_);
        contentValues.put(DBHelper.TABLE_COLUMN_LOCATION, place_);
        contentValues.put(DBHelper.TABLE_COLUMN_DATE, date_);
        open();
        database.insert(DBHelper.TABLE_TICKETS, null, contentValues);
        close();
    }

    public void editTableRowTicket(Integer _id, String film_, Integer place_, String date_) {
        database = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_COLUMN_FILM, film_);
        contentValues.put(DBHelper.TABLE_COLUMN_LOCATION, place_);
        contentValues.put(DBHelper.TABLE_COLUMN_DATE, date_);

        String[] whereArgs = new String[]{String.valueOf(_id)};

        database.update(DBHelper.TABLE_TICKETS, contentValues, "_id=?", whereArgs);
    }

    public void deleteTableRow(Integer _id) {
        open();
        database.delete(DBHelper.TABLE, "_id=" + _id, null);
        close();
    }
}