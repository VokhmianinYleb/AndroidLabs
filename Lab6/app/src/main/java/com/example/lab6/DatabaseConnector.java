// DatabaseConnector.java

package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Клас DatabaseConnector для підключення до бази даних
 * Зберігає назву бази даних, об'єкт бази даних та об'єкт класу DBHelper
 * Має методи відкриття та закриття бази даних для запису даних,
 * додавання рядків до бази даних,
 * отримання курсору бази даних за певним унікальним ідентифікатором,
 * отримання курсору бази даних, який надає доступ до результатів запиту,
 * редагування рядків за їх унікальним ідентифікатором,
 * видалення рядків за їх унікальним ідентифікатором
 */
public class DatabaseConnector {
    private static final String DATABASE_NAME = "HotelDB";
    private SQLiteDatabase database;
    private DBHelper databaseOpenHelper;

    // Конструктор
    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DBHelper(context, DATABASE_NAME, null, 3);
    }

    /**
     * Метод для створення/відкриття бази даних, яка буде використана для зчитування та запису даних
     * Після відкриття база даних кешується, тому можемо викликати цей метод щоразу, коли виникає необхідність
     * зробити запис до бази даних
     */
    public void open() {
        database = databaseOpenHelper.getWritableDatabase();
    }

    /**
     * Метод для закриття підключення до бази даних
     */
    public void close() {
        if (database != null) {
            database.close();
        }
    }

    /**
     * Повертає курсор бази даних за певним унікальним ідентифікатором
     * @param idForEdit унікальний ідентифікатор
     * @return курсор бази даних
     */
    public Cursor getTableRowByID(Integer idForEdit) {
        return database.query(
            DBHelper.TABLE,
            new String[] {
                DBHelper.TABLE_COLUMN_ID,
                DBHelper.TABLE_COLUMN_PIB,
                DBHelper.TABLE_COLUMN_PHONENUMBER,
                DBHelper.TABLE_COLUMN_PASSPORT,
            },
            "_id=?",
            new String[] {String.valueOf(idForEdit)},
            null,
            null,
            null
        );
    }

    /**
     * Повертає значення курсору для доступу до результатів запиту
     * @return курсор для доступу до результатів запиту
     */
    public Cursor getTableAllRows() { // одержання курсору для доступу до результатів запиту
        return database.query(
            DBHelper.TABLE,
            new String[] {
                DBHelper.TABLE_COLUMN_ID,
                DBHelper.TABLE_COLUMN_PIB,
                DBHelper.TABLE_COLUMN_PHONENUMBER,
                DBHelper.TABLE_COLUMN_PASSPORT,
            },
            null,
            null,
            null,
            null,
            DBHelper.TABLE_COLUMN_ID
        );
    }

    /**
     * Додає новий рядок до бази даних
     * @param _id унікальний ідентифікатор
     * @param PIB прізвище, ім'я, по-батькові
     * @param phoneNumber номер телефону
     * @param passport серія та номер паспорта
     */
    public void insertRow(Integer _id, String PIB, String phoneNumber, String passport) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_COLUMN_ID, _id);
        contentValues.put(DBHelper.TABLE_COLUMN_PIB, PIB);
        contentValues.put(DBHelper.TABLE_COLUMN_PHONENUMBER, phoneNumber);
        contentValues.put(DBHelper.TABLE_COLUMN_PASSPORT, passport);
        open();
        database.insert(DBHelper.TABLE, null, contentValues);
        close();
    }

    /**
     * Редагує рядок у базі даних
     * @param _id унікальний ідентифікатор (старий)
     * @param newID унікальний ідентифікатор (новий)
     * @param PIB прізвище, ім'я, по-батькові
     * @param phoneNumber номер телефону
     * @param passport серія та номер паспорта
     */
    public void editTableRow(Integer _id, Integer newID, String PIB, String phoneNumber, String passport) {
        database = databaseOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TABLE_COLUMN_ID, newID);
        contentValues.put(DBHelper.TABLE_COLUMN_PIB, PIB);
        contentValues.put(DBHelper.TABLE_COLUMN_PHONENUMBER, phoneNumber);
        contentValues.put(DBHelper.TABLE_COLUMN_PASSPORT, passport);

        String[] whereArgs = new String[]{String.valueOf(_id)};

        database.update(DBHelper.TABLE, contentValues, "_id=?", whereArgs);
    }

    /**
     * Видаляє рядок з бази даних за його унікальним ідентифікатором
     * @param _id унікальний ідентифікатор
     */
    public void deleteTableRow(Integer _id) {
        open();
        database.delete(DBHelper.TABLE, "_id=" + _id, null);
        close();
    }
}