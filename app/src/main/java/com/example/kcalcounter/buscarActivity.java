package com.example.kcalcounter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class buscarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText e1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        //Icono en el activity.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        e1 = findViewById(R.id.txt_calorias);
        tv1 = findViewById(R.id.tv_fecha);
        Button button = findViewById(R.id.btn_fecha);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View r) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    //Método para hacer consulta en la base de datos.
    public void buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fechaSeleccionada = tv1.getText().toString();

        if (!fechaSeleccionada.isEmpty()) {
            Cursor fila =  BaseDeDatos.rawQuery("select * from calorias",  null);
            if (fila.moveToFirst()) {
                e1.setText(fila.getString(1));
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No se registraron calorías para ese día.", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "No se seleccionó ninguna fecha en la ventana anterior, vuelva y seleccione una.", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverHome(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        tv1.setText(currentDateString);
    }
}
