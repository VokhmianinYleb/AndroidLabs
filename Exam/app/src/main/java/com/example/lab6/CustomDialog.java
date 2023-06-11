package com.example.lab6;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog extends Dialog {
    private Button cancelButton, addButton;
    private EditText editTextPlaceID, editTextRow, editTextView;
    private DatabaseConnector databaseConnector;

    public CustomDialog(Context context, Places activity, Integer idForEdit) {
        super(context);
        setContentView(R.layout.dialog_layout);

        this.cancelButton = (Button) this.findViewById(R.id.cancel_button);
        this.addButton = (Button) this.findViewById(R.id.add_button);
        this.editTextPlaceID = (EditText) this.findViewById(R.id.editTextFilm);
        this.editTextRow = (EditText) this.findViewById(R.id.editTextLocation);
        this.editTextView = (EditText) this.findViewById(R.id.editTextDate);

        this.databaseConnector = new DatabaseConnector(this.getContext());

        if (idForEdit != null) {
            this.addButton.setText("Редагувати"); // зміна назви кнопки

            this.databaseConnector.open();
            Cursor cursor = this.databaseConnector.getTableRowByID(idForEdit);

            if (cursor.moveToFirst()) {
                this.editTextPlaceID.setText(cursor.getString(0));
                this.editTextRow.setText(cursor.getString(1));
                this.editTextView.setText(cursor.getString(2));
            }

            this.databaseConnector.close();
        }

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog.super.dismiss(); // закриття вікна
            }
        });

        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer placeID, row_;
                String view_;

                try {
                    placeID = Integer.parseInt(editTextPlaceID.getText().toString());
                    row_ = Integer.parseInt(editTextRow.getText().toString());
                    view_ = editTextView.getText().toString();
                }
                catch (Exception as) {
                    Toast.makeText(view.getContext(), "Помилка! Перевірте введені типи!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (idForEdit == null) {
                    databaseConnector.insertRow(placeID, row_, view_);
                }
                else {
                    try {
                        databaseConnector.editTableRow(idForEdit, placeID, row_, view_);
                    }
                    catch (android.database.sqlite.SQLiteConstraintException as) {
                        Toast.makeText(view.getContext(), "Виникла помилка редагування.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                new GetRowsTask(activity).execute();
                Toast.makeText(view.getContext(), "Список оновлено!", Toast.LENGTH_SHORT).show();
                CustomDialog.super.dismiss(); // закриття вікна
        }});

        this.show();
    }
}