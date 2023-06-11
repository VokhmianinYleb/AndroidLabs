package com.example.lab6;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialogTickets extends Dialog {
    private Button cancelButton, addButton;
    private EditText editTextFilm, editTextLocation, editTextDate;
    private DatabaseConnector databaseConnector;

    public CustomDialogTickets(Context context, Tickets activity, Integer idForEdit) {
        super(context);
        setContentView(R.layout.dialog_tickets);

        this.cancelButton = (Button) this.findViewById(R.id.cancel_button);
        this.addButton = (Button) this.findViewById(R.id.add_button);
        this.editTextFilm = (EditText) this.findViewById(R.id.editTextFilm);
        this.editTextLocation = (EditText) this.findViewById(R.id.editTextLocation);
        this.editTextDate = (EditText) this.findViewById(R.id.editTextDate);

        this.databaseConnector = new DatabaseConnector(this.getContext());

        if (idForEdit != null) {
            this.addButton.setText("Редагувати"); // зміна назви кнопки

            this.databaseConnector.open();
            Cursor cursor = this.databaseConnector.getTableRowByIDTicket(idForEdit);

            if (cursor.moveToFirst()) {
                this.editTextFilm.setText(cursor.getString(0));
                this.editTextLocation.setText(cursor.getString(1));
                this.editTextDate.setText(cursor.getString(2));
            }

            this.databaseConnector.close();
        }

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogTickets.super.dismiss(); // закриття вікна
            }
        });

        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer place_id;
                String film_, date_;

                try {
                    film_ = editTextFilm.getText().toString();
                    place_id = Integer.parseInt(editTextLocation.getText().toString());
                    date_ = editTextDate.getText().toString();
                }
                catch (Exception as) {
                    Toast.makeText(view.getContext(), "Необхідно заповнити усі поля!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (idForEdit == null) {
                    databaseConnector.insertRowTicket(film_, place_id, date_);
                }
                else {
                    try {
                        databaseConnector.editTableRowTicket(idForEdit, film_, place_id, date_);
                    }
                    catch (android.database.sqlite.SQLiteConstraintException as) {
                        Toast.makeText(view.getContext(), "Виникла помилка!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                new GetRowsTickets(activity).execute();
                Toast.makeText(view.getContext(), "Список оновлено!", Toast.LENGTH_SHORT).show();
                CustomDialogTickets.super.dismiss();
        }});

        this.show();
    }
}