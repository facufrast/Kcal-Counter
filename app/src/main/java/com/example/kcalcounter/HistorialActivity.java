package com.example.kcalcounter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class HistorialActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView tv1, tv2, tv3;
    private ListView lv1;

    private String dias [] = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        //Icono en el activity.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        tv1 = (TextView)findViewById(R.id.tv_calorias);
        lv1 = (ListView)findViewById(R.id.lv_historial);
        tv3 = findViewById(R.id.tv_calorias_busqueda);
        tv2 = findViewById(R.id.tv_fecha);
        Button button = findViewById(R.id.btn_fecha);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View r) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        tv3.setEnabled(false);

        String domingo = getIntent().getStringExtra("domingo");
        String lunes = getIntent().getStringExtra("lunes");
        String martes = getIntent().getStringExtra("martes");
        String miercoles = getIntent().getStringExtra("miercoles");
        String jueves = getIntent().getStringExtra("jueves");
        String viernes = getIntent().getStringExtra("viernes");
        String sabado = getIntent().getStringExtra("sabado");

        //Asignación del valor "0" en caso de que el parámtro pasado no tenga declaración previa.
        if (domingo.equals("") == true) {
            domingo = "0";
        }

        if (lunes.equals("") == true) {
            lunes = "0";
        }

        if (martes.equals("") == true) {
            martes = "0";
        }

        if (miercoles.equals("") == true) {
            miercoles = "0";
        }

        if (jueves.equals("") == true) {
            jueves = "0";
        }

        if (viernes.equals("") == true) {
            viernes = "0";
        }

        if (sabado.equals("") == true) {
            sabado = "0";
        }

        final String calorias [] = {domingo, lunes, martes, miercoles, jueves, viernes, sabado};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_historial, dias);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText("Las calorias restantes del día " + lv1.getItemAtPosition(position) + " son " + calorias[position] + " calorías.");
            }
        });
    }

    //Método para hacer consulta en la base de datos.
    public void buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fechaSeleccionada = tv2.getText().toString();

        if (!fechaSeleccionada.isEmpty()) {
            Cursor fila =  BaseDeDatos.rawQuery("select * from calorias",  null);
            if (fila.moveToFirst()) {
                tv3.setText(fila.getString(1) + " kcal");
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No se registraron calorías para ese día.", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "No se seleccionó ninguna fecha en la ventana anterior, vuelva y seleccione una.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        tv2.setText(currentDateString);
        tv3.setEnabled(true);
    }

    public void volverHome(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
