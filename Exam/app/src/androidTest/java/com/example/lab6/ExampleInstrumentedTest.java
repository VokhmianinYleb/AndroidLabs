package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.lab6", appContext.getPackageName());
    }

    private DatabaseConnector databaseConnector;

    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector(InstrumentationRegistry.getInstrumentation().getTargetContext());
        databaseConnector.open();
    }

    @After
    public void tearDown() {
        databaseConnector.close();
    }

    @Test
    public void testInsertRow() {
        databaseConnector.insertRow(1, 1, "view1");

        Cursor cursor = databaseConnector.getTableAllRows();
        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(1, cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_PLACE_ID)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_ROW)));
        assertEquals("view1", cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_VIEW)));

        databaseConnector.deleteTableRow(1);
    }

    @Test
    public void testEditTableRow() {
        databaseConnector.insertRow(1, 1, "view1");

        databaseConnector.editTableRow(1, 2, 2, "view2");

        Cursor cursor = databaseConnector.getTableRowByID(1);
        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(2, cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_PLACE_ID)));
        assertEquals(2, cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_ROW)));
        assertEquals("view2", cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_VIEW)));

        databaseConnector.deleteTableRow(2);
    }

    @Test
    public void testDeleteTableRow() {
        databaseConnector.insertRow(1, 1, "view1");

        databaseConnector.deleteTableRow(1);

        Cursor cursor = databaseConnector.getTableAllRows();
        assertNotNull(cursor);
        assertFalse(cursor.moveToFirst());
    }

    @Test
    public void testInsertRowTicket() {
        databaseConnector.insertRowTicket("film1", 1, "2023-06-11");

        Cursor cursor = databaseConnector.getTableAllTickets();
        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals("film1", cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_FILM)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_LOCATION)));
        assertEquals("2023-06-11", cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_DATE)));

        databaseConnector.deleteTableRowTicket(1);
    }

    @Test
    public void testEditTableRowTicket() {
        databaseConnector.insertRowTicket("film1", 1, "2023-06-11");

        databaseConnector.editTableRowTicket(1, "film2", 2, "2023-06-12");

        Cursor cursor = databaseConnector.getTableRowByIDTicket(1);
        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals("film2", cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_FILM)));
        assertEquals(2, cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_LOCATION)));
        assertEquals("2023-06-12", cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_DATE)));

        databaseConnector.deleteTableRowTicket(1);
    }

    @Test
    public void testDeleteTableRowTicket() {
        databaseConnector.insertRowTicket("film1", 1, "2023-06-11");

        databaseConnector.deleteTableRowTicket(1);

        Cursor cursor = databaseConnector.getTableAllTickets();
        assertNotNull(cursor);
        assertFalse(cursor.moveToFirst());
    }
}
