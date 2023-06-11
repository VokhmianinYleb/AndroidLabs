package com.example.lab6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE = "Places";
    public static final String TABLE_COLUMN_PLACE_ID = "_id";
    public static final String TABLE_COLUMN_ROW = "row";
    public static final String TABLE_COLUMN_VIEW = "view";

    public static final String TABLE_TICKETS = "Tickets";
    public static final String TABLE_COLUMN_TICKET_ID = "_id";
    public static final String TABLE_COLUMN_FILM = "film";
    public static final String TABLE_COLUMN_LOCATION = "place_id";
    public static final String TABLE_COLUMN_DATE = "customdate";

    // Контруктор
    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format(
                        "CREATE TABLE %s (" +
                                "`%s` INTEGER PRIMARY KEY, " +
                                "`%s` INTEGER, " +
                                "`%s` TEXT " +
                                ")",
                        TABLE,
                        TABLE_COLUMN_PLACE_ID,
                        TABLE_COLUMN_ROW,
                        TABLE_COLUMN_VIEW
                )
        );

        db.execSQL(
			String.format(
				"CREATE TABLE %s (" +
					"`%s` INTEGER PRIMARY KEY, " +
					"`%s` TEXT, " +
					"`%s` INTEGER, " +
					"`%s` DATE, " +
					"FOREIGN KEY (`%s`) REFERENCES %s (`%s`)" +
				")",
				TABLE_TICKETS,
				TABLE_COLUMN_TICKET_ID,
				TABLE_COLUMN_FILM,
				TABLE_COLUMN_LOCATION,
				TABLE_COLUMN_DATE,
				TABLE_COLUMN_LOCATION,
				TABLE,
				TABLE_COLUMN_PLACE_ID
			)
		);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}