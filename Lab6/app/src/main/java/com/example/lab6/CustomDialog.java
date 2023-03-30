// CustomDialog.java

package com.example.lab6;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Клас для створення діалогового вікна для користувача, де він може ввести свої дані
 * Зберігає покажчики на компоненти інтерфейсу
 */
public class CustomDialog extends Dialog {
    private Button cancelButton, addButton;
    private EditText editTextNumberRoom, editTextPIB, editTextPhoneNumber, editTextPassport;
    private DatabaseConnector databaseConnector;

    /**
     * Конструктор, який виконує усю функціональність діалогового вікна
     * @param context контекст
     * @param activity батьківска активність
     * @param idForEdit унікальний ідентифікатор за яким буде відбуватись редагування (якщо не вказаний, то додавання)
     */
    public CustomDialog(Context context, MainActivity activity, Integer idForEdit) {
        super(context); // конструктор батьківського класу
        setContentView(R.layout.dialog_layout); // встановлюємо інтерфейс з dialog_layout

        // Отримання покажчиків на компоненти вікна
        this.cancelButton = (Button) this.findViewById(R.id.cancel_button);
        this.addButton = (Button) this.findViewById(R.id.add_button);
        this.editTextNumberRoom = (EditText) this.findViewById(R.id.editTextNumberRoom);
        this.editTextPIB = (EditText) this.findViewById(R.id.editTextPIB);
        this.editTextPhoneNumber = (EditText) this.findViewById(R.id.editTextPhoneNumber);
        this.editTextPassport = (EditText) this.findViewById(R.id.editTextPassport);

        // Підключення до бази даних
        this.databaseConnector = new DatabaseConnector(this.getContext());

        // Якщо вказаний унікальний ідентифікатор запису, встановлюємо компонентам їх значення
        // для подальшого редагування
        if (idForEdit != null) {
            this.addButton.setText("Редагувати"); // зміна назви кнопки

            // Відкриваємо з'єднання з базою даних та отримуємо курсор за вказаним ID
            this.databaseConnector.open();
            Cursor cursor = this.databaseConnector.getTableRowByID(idForEdit);

            // Якщо дані знайдені, встановлюємо їх значення у компоненти
            if (cursor.moveToFirst()) {
                this.editTextNumberRoom.setText(cursor.getString(0));
                this.editTextPIB.setText(cursor.getString(1));
                this.editTextPhoneNumber.setText(cursor.getString(2));
                this.editTextPassport.setText(cursor.getString(3));
            }

            // Закриваємо з'єднання з базою даних
            this.databaseConnector.close();
        }

        // Обробник подій при натисканні на кнопку скасування
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog.super.dismiss(); // закриття вікна
            }
        });

        // Обробник подій при натисканні на кнопку додавання / редагування
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Оголошеня змінних для значень у компонентах
                Integer numberRoom;
                String PIB, phoneNumber, passport;

                // одержання даних з вікна
                try {
                    numberRoom = Integer.parseInt(editTextNumberRoom.getText().toString());
                    PIB = editTextPIB.getText().toString();
                    phoneNumber = editTextPhoneNumber.getText().toString();
                    passport = editTextPassport.getText().toString();
                }
                catch (Exception as) { // якщо сталася помилка, повідомлюємо користувача
                    Toast.makeText(view.getContext(), "Необхідно заповнити усі поля!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Якщо унікальний ідентифікатор не вказано, додаємо новий рядок до бази даних
                if (idForEdit == null) {
                    databaseConnector.insertRow(numberRoom, PIB, phoneNumber, passport);
                }
                else { // якщо ж вказаний, редагуємо
                    try {
                        databaseConnector.editTableRow(idForEdit, numberRoom, PIB, phoneNumber, passport);
                    }
                    catch (android.database.sqlite.SQLiteConstraintException as) {
                        // якщо сталася помилка, пов'язана з тим, що користувач змінив унікальний індентифікатор
                        // на той, що вже існує, повідомлюємо йому про це
                        Toast.makeText(view.getContext(), "Ви не можете змінити номер кімнати на той, що вже існує.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                // оновлюємо список
                new GetRowsTask(activity).execute();

                // виведення на екран повідомлення про виконану дію
                Toast.makeText(view.getContext(), "Список оновлено!", Toast.LENGTH_SHORT).show();

                CustomDialog.super.dismiss(); // закриття вікна
        }});

        this.show(); // відкриття вікна
    }
}