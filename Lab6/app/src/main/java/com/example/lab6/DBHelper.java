// DBHelper.java

package com.example.lab6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Клас DBHelper для керування базою даних
 * Зберігає назву таблиці та змінні для збережння рядків
 * Створює таблицю використовуючи запит мовою SQL
 */
class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE = "Rooms"; // назва таблиці
    public static final String TABLE_COLUMN_ID = "_id"; // унікальний ідентифікатор рядка
    public static final String TABLE_COLUMN_PIB = "pib"; // прізвище, ім'я, по-батькові
    public static final String TABLE_COLUMN_PHONENUMBER = "phoneNumber"; // номер телефону
    public static final String TABLE_COLUMN_PASSPORT = "passport"; // серія та номер паспорта

    // Контруктор
    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
    }

    /**
     * Створює нову таблицю
     * @param db об'єкт для керування базою даних SQLite, що має методи
     * загальних задач (створення, оновлення, видалення тощо)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            String.format(
                "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT" +
                ")",
                TABLE,
                TABLE_COLUMN_ID,
                TABLE_COLUMN_PIB,
                TABLE_COLUMN_PHONENUMBER,
                TABLE_COLUMN_PASSPORT
            )
        );
    }

    /**
     * Оновлює базу даних до необхідної версії, яка може бути вказана в конструкторі
     * @param db об'єкт для керування базою даних SQLite, що має методи загальних задач (створення, оновлення, видалення тощо)
     * @param oldVersion стара версія бази даних
     * @param newVersion нова версія бази даних
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}