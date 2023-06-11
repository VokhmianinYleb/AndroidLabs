package com.example.lab6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Places extends AppCompatActivity {
    private static final String TAG = "HotelRooms";
    private ArrayList<String> arrayList;

    private final int MENU_CONTEXT_DELETE_ID = 123;
    private final int MENU_CONTEXT_EDIT_ID = 124;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        this.listView = findViewById(R.id.listViewHotelRooms);
        registerForContextMenu(this.listView);

        Button button = (Button)findViewById(R.id.buttonCallDialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog(v.getContext(), Places.this, null);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh_screen();
    }

    void refresh_screen() {
        new GetRowsTask(this).execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        if (view.getId() == R.id.listViewHotelRooms) {
            // Створення опцій меню
            menu.add(Menu.NONE, MENU_CONTEXT_DELETE_ID, Menu.NONE, "Видалення");
            menu.add(Menu.NONE, MENU_CONTEXT_EDIT_ID, Menu.NONE, "Редагування");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // одержання id рядка
        Integer _id = Integer.parseInt(this.arrayList.get(info.position).split(" ")[0].substring(1));

        switch (item.getItemId()) {
            case MENU_CONTEXT_DELETE_ID: { // видалення елемента з бази даних
                Log.d(TAG, "видалення елементу " + info.position); // запис інформації у журнал

                DatabaseConnector databaseConnector = new DatabaseConnector(this);
                databaseConnector.deleteTableRow(_id);

                refresh_screen();
                Toast.makeText(this, "Елемент було видалено", Toast.LENGTH_SHORT).show();
                return true;
            }
            case MENU_CONTEXT_EDIT_ID: { // редагування елемента
                Log.d(TAG, "редагування елементу =" + info.position); // запис інформації у журнал
                new CustomDialog(this, this, _id);
                Toast.makeText(this, "Редагування елемента", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void update_list(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        this.listView.setAdapter(listAdapter);
    }
}