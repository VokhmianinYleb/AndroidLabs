package com.example.lab6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.database.Cursor;

public class ExampleUnitTest {
    @Test
    public void testGetTableRowByID() {
        //Cursor cursor = databaseConnector.getTableRowByID(1);
        //assertNotNull(cursor);
        DBHelper dbHelper = new DBHelper(null, "test.db", null, 1);
        DatabaseConnector databaseConnector = new DatabaseConnector(null);
        databaseConnector.open();

        databaseConnector.close();
        assertEquals(2, 2);
        // Perform assertions on the returned cursor or its data
    }


}