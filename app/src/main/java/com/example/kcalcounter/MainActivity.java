package com.example.kcalcounter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText et1, et2, et3, et4, et5, et6, et7;
    private TextView tv1, fechaSeleccionada, acumulado;
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15;
    private Spinner spinner1;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Icono en el activity.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        et1 = findViewById(R.id.txt_domingo);
        et2 = findViewById(R.id.txt_lunes);
        et3 = findViewById(R.id.txt_martes);
        et4 = findViewById(R.id.txt_miercoles);
        et5 = findViewById(R.id.txt_jueves);
        et6 = findViewById(R.id.txt_viernes);
        et7 = findViewById(R.id.txt_sabado);
        tv1 = findViewById(R.id.txt_total);
        fechaSeleccionada = findViewById(R.id.tv_fecha);
        r1 = findViewById(R.id.radioButton1);
        r2 = findViewById(R.id.radioButton2);
        r3 = findViewById(R.id.radioButton3);
        r4 = findViewById(R.id.radioButton4);
        r5 = findViewById(R.id.radioButton5);
        r6 = findViewById(R.id.radioButton6);
        r7 = findViewById(R.id.radioButton7);
        r8 = findViewById(R.id.radioButton8);
        r9 = findViewById(R.id.radioButton9);
        r10 = findViewById(R.id.radioButton10);
        r11 = findViewById(R.id.radioButton11);
        r12 = findViewById(R.id.radioButton12);
        r13 = findViewById(R.id.radioButton13);
        r14 = findViewById(R.id.radioButton14);
        r15 = findViewById(R.id.radioButton15);
        spinner1 = findViewById(R.id.spinner_dias);
        Button button = findViewById(R.id.btn_fecha);
        acumulado = findViewById(R.id.tv_acumulado);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View r) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        String [] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_dias, dias);
        spinner1.setAdapter(adapter);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        et1.setText(preferences.getString("domingo", ""));
        et2.setText(preferences.getString("lunes", ""));
        et3.setText(preferences.getString("martes", ""));
        et4.setText(preferences.getString("miercoles", ""));
        et5.setText(preferences.getString("jueves", ""));
        et6.setText(preferences.getString("viernes", ""));
        et7.setText(preferences.getString("sabado", ""));
        String total = preferences.getString("total", "");
        tv1.setText(total + " kcal");
        String ac = preferences.getString("ac", "");
        acumulado.setText(ac + " kcal AC");
        r1.setChecked(preferences.getBoolean("gym1", false));
        r3.setChecked(preferences.getBoolean("gym2", false));
        r5.setChecked(preferences.getBoolean("gym3", false));
        r7.setChecked(preferences.getBoolean("gym4", false));
        r9.setChecked(preferences.getBoolean("gym5", false));
        r11.setChecked(preferences.getBoolean("gym6", false));
        r13.setChecked(preferences.getBoolean("gym7", false));
        r2.setChecked(preferences.getBoolean("leg1", false));
        r4.setChecked(preferences.getBoolean("leg2", false));
        r6.setChecked(preferences.getBoolean("leg3", false));
        r8.setChecked(preferences.getBoolean("leg4", false));
        r10.setChecked(preferences.getBoolean("leg5", false));
        r12.setChecked(preferences.getBoolean("leg6", false));
        r14.setChecked(preferences.getBoolean("leg7", false));
//        r15.setChecked(preferences.getBoolean("full_week", false));
        r15.setChecked(false);

        //Validaciones para que deje deshabilitado los casilleros que ya contienen datos.
        if (et1.getText().toString().equals("")) {
            et1.setEnabled(true);
        }
        else {
            et1.setEnabled(false);
        }

        if (et2.getText().toString().equals("")) {
            et2.setEnabled(true);
        }
        else {
            et2.setEnabled(false);
        }

        if (et3.getText().toString().equals("")) {
            et3.setEnabled(true);
        }
        else {
            et3.setEnabled(false);
        }

        if (et4.getText().toString().equals("")) {
            et4.setEnabled(true);
        }
        else {
            et4.setEnabled(false);
        }

        if (et5.getText().toString().equals("")) {
            et5.setEnabled(true);
        }
        else {
            et5.setEnabled(false);
        }

        if (et6.getText().toString().equals("")) {
            et6.setEnabled(true);
        }
        else {
            et6.setEnabled(false);
        }

        if (et7.getText().toString().equals("")) {
            et7.setEnabled(true);
        }
        else {
            et7.setEnabled(false);
        }

        //Validaciones para que los radio buttons de "Leg Day" estén deshabilitados si no está seleccionado el correspondiente radio button de "Gym", y los
//        radio buttons de "Gym" también se deshabiliten en caso de estar seleccionados.
        if (!r1.isChecked()) {
            r2.setEnabled(false);
        }
        else {
            r1.setEnabled(false);
        }

        if (!r3.isChecked()) {
            r4.setEnabled(false);
        }
        else {
            r3.setEnabled(false);
        }

        if (!r5.isChecked()) {
            r6.setEnabled(false);
        }
        else {
            r5.setEnabled(false);
        }

        if (!r7.isChecked()) {
            r8.setEnabled(false);
        }
        else {
            r7.setEnabled(false);
        }

        if (!r9.isChecked()) {
            r10.setEnabled(false);
        }
        else {
            r9.setEnabled(false);
        }

        if (!r11.isChecked()) {
            r12.setEnabled(false);
        }
        else {
            r11.setEnabled(false);
        }

        if (!r13.isChecked()) {
            r14.setEnabled(false);
        }
        else {
            r13.setEnabled(false);
        }

        //Validaciones para que los radio buttons de "Leg Day" queden deshabilitados si están seleccionados.
        if (r2.isChecked()) {
            r2.setEnabled(false);
        }

        if (r4.isChecked()) {
            r4.setEnabled(false);
        }

        if (r6.isChecked()) {
            r6.setEnabled(false);
        }

        if (r8.isChecked()) {
            r8.setEnabled(false);
        }

        if (r10.isChecked()) {
            r10.setEnabled(false);
        }

        if (r12.isChecked()) {
            r12.setEnabled(false);
        }

        if (r14.isChecked()) {
            r14.setEnabled(false);
        }

    }

    //Método para cerrar la app con doble toque en el botón back.
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Toque el botón de atrás de vuelta para salir.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

//    //Método para mostrar los botones de acción.
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menubuscar, menu);
//        return true;
//    }
//
//    //Método para agregar las acciones de los botones de acción.
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.buscar) {
//            Intent i = new Intent(this, buscarActivity.class);
//            startActivity(i);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //Método que calcula la cantidad de calorías totales.
    public void calcularTotal(View view) {
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        String domingo = et1.getText().toString();
        String lunes = et2.getText().toString();
        String martes = et3.getText().toString();
        String miercoles = et4.getText().toString();
        String jueves = et5.getText().toString();
        String viernes = et6.getText().toString();
        String sabado = et7.getText().toString();
        String actual = acumulado.getText().toString();

        //Validaciones para considerar valores no ingresados (los seteo en 0).
        if (domingo.equals("")) {
            domingo = "0";
        }

        if (lunes.equals("" )) {
            lunes = "0";
        }

        if (martes.equals("")) {
            martes = "0";
        }

        if (miercoles.equals("")) {
            miercoles = "0";
        }

        if (jueves.equals("")) {
            jueves = "0";
        }

        if (viernes.equals("")) {
            viernes = "0";
        }

        if (sabado.equals("")) {
            sabado = "0";
        }

        //Declaro variables Int para hacer la suma de los valores.
        int num1 = Integer.parseInt(domingo);
        int num2 = Integer.parseInt(lunes);
        int num3 = Integer.parseInt(martes);
        int num4 = Integer.parseInt(miercoles);
        int num5 = Integer.parseInt(jueves);
        int num6 = Integer.parseInt(viernes);
        int num7 = Integer.parseInt(sabado);

        //Validaciones para tener en cuenta los radio buttons seleccionados.
        if (r1.isChecked()) {
            num1 += 200;
            if (r2.isChecked()) {
                num1 += 100;
            }
        }

        if (r3.isChecked()) {
            num2 += 200;
            if (r4.isChecked()) {
                num2 += 100;
            }
        }

        if (r5.isChecked()) {
            num3 += 200;
            if (r6.isChecked()) {
                num3 += 100;
            }
        }

        if (r7.isChecked()) {
            num4 += 200;
            if (r8.isChecked()) {
                num4 += 100;
            }
        }

        if (r9.isChecked()) {
            num5 += 200;
            if (r10.isChecked()) {
                num5 += 100;
            }
        }

        if (r11.isChecked()) {
            num6 += 200;
            if (r12.isChecked()) {
                num6 += 100;
            }
        }

        if (r13.isChecked()) {
            num7 += 200;
            if (r14.isChecked()) {
                num7 += 100;
            }
        }

        int suma = num1 + num2 + num3 + num4 + num5 + num6 + num7;

        String resultado = String.valueOf(suma);

        tv1.setText(resultado + " kcal");

        if (r15.isChecked()){
//            int sumaAcumulado = num1 + num2 + num3 + num4 + num5 + num6 + num7;
//            String ac = String.valueOf(sumaAcumulado);
            acumulado.setText(resultado + " kcal AC");
            editor.putString("ac", resultado);
//            if (!(acumulado.getText().equals(""))) {
//                ac = String.valueOf(sumaAcumulado + actualAcumulado);
//                acumulado.setText(ac + " kcal AC");
//                editor.putString("ac", ac);
//            }
        }
        else {
            acumulado.setText("");
        }

        //Guardo los valores en las Shared Preferences.
        editor.putString("total", resultado);
        editor.commit();

    }

    //Método que guarda los datos ingresados.
    public void guardarDatos(View view) {
        Intent main = new Intent(this, MainActivity.class);
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        //Validaciones para que deje deshabilitados los casilleros que ya contienen datos.
        if (et1.getText().toString().equals("")) {
            et1.setEnabled(true);
        }
        else {
            et1.setEnabled(false);
        }

        if (et2.getText().toString().equals("")) {
            et2.setEnabled(true);
        }
        else {
            et2.setEnabled(false);
        }

        if (et3.getText().toString().equals("")) {
            et3.setEnabled(true);
        }
        else {
            et3.setEnabled(false);
        }

        if (et4.getText().toString().equals("")) {
            et4.setEnabled(true);
        }
        else {
            et4.setEnabled(false);
        }

        if (et5.getText().toString().equals("")) {
            et5.setEnabled(true);
        }
        else {
            et5.setEnabled(false);
        }

        if (et6.getText().toString().equals("")) {
            et6.setEnabled(true);
        }
        else {
            et6.setEnabled(false);
        }

        if (et7.getText().toString().equals("")) {
            et7.setEnabled(true);
        }
        else {
            et7.setEnabled(false);
        }

        editor.putString("domingo", et1.getText().toString());
        editor.putString("lunes", et2.getText().toString());
        editor.putString("martes", et3.getText().toString());
        editor.putString("miercoles", et4.getText().toString());
        editor.putString("jueves", et5.getText().toString());
        editor.putString("viernes", et6.getText().toString());
        editor.putString("sabado", et7.getText().toString());
        editor.putBoolean("gym1", r1.isChecked());
        editor.putBoolean("gym2", r3.isChecked());
        editor.putBoolean("gym3", r5.isChecked());
        editor.putBoolean("gym4", r7.isChecked());
        editor.putBoolean("gym5", r9.isChecked());
        editor.putBoolean("gym6", r11.isChecked());
        editor.putBoolean("gym7", r13.isChecked());
        editor.putBoolean("leg1", r2.isChecked());
        editor.putBoolean("leg2", r4.isChecked());
        editor.putBoolean("leg3", r6.isChecked());
        editor.putBoolean("leg4", r8.isChecked());
        editor.putBoolean("leg5", r10.isChecked());
        editor.putBoolean("leg6", r12.isChecked());
        editor.putBoolean("leg7", r14.isChecked());
        editor.commit();

        calcularTotal(view);
        Toast.makeText(this, "Datos guardados con éxito.", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(main);
    }

    //Método que permite la edición de los valores ingresados.
    public void editarCalorias(View view) {
        String seleccion = spinner1.getSelectedItem().toString();

        if (seleccion.equals("Domingo")) {
            et1.setEnabled(true);
            r1.setChecked(false);
            r1.setEnabled(true);
            r2.setChecked(false);
        }

        if (seleccion.equals("Lunes")) {
            et2.setEnabled(true);
            r3.setChecked(false);
            r3.setEnabled(true);
            r4.setChecked(false);
        }

        if (seleccion.equals("Martes")) {
            et3.setEnabled(true);
            r5.setChecked(false);
            r5.setEnabled(true);
            r6.setChecked(false);
        }

        if (seleccion.equals("Miércoles")) {
            et4.setEnabled(true);
            r7.setChecked(false);
            r7.setEnabled(true);
            r8.setChecked(false);
        }

        if (seleccion.equals("Jueves")) {
            et5.setEnabled(true);
            r9.setChecked(false);
            r9.setEnabled(true);
            r10.setChecked(false);
        }

        if (seleccion.equals("Viernes")) {
            et6.setEnabled(true);
            r11.setChecked(false);
            r11.setEnabled(true);
            r12.setChecked(false);
        }

        if (seleccion.equals("Sábado")) {
            et7.setEnabled(true);
            r13.setChecked(false);
            r13.setEnabled(true );
            r14.setChecked(false);
        }
    }

    //Método que permite borrar los datos guardados.
    public void borrarDatos(View view) {
        final Intent main = new Intent(this, MainActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de borrar los datos?");
        builder.setTitle("Confirmación de Borrado");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("domingo", "");
                editor.putString("lunes", "");
                editor.putString("martes", "");
                editor.putString("miercoles", "");
                editor.putString("jueves", "");
                editor.putString("viernes", "");
                editor.putString("sabado", "");
                editor.putString("total", "");
                editor.putBoolean("gym1", false);
                editor.putBoolean("gym2", false);
                editor.putBoolean("gym3", false);
                editor.putBoolean("gym4", false);
                editor.putBoolean("gym5", false);
                editor.putBoolean("gym6", false);
                editor.putBoolean("gym7", false);
                editor.putBoolean("leg1", false);
                editor.putBoolean("leg2", false);
                editor.putBoolean("leg3", false);
                editor.putBoolean("leg4", false);
                editor.putBoolean("leg5", false);
                editor.putBoolean("leg6", false);
                editor.putBoolean("leg7", false);
                if (r15.isChecked()) {
                    editor.putString("ac", "");
                }
                editor.commit();
                finish();
                startActivity(main);
                Toast.makeText(MainActivity.this, "Datos Borrados con éxito.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Método que permite limpiar los campos sin guardar.
    public void limpiarDatos(View view) {
        if (r1.isEnabled() && r1.isChecked() ) {
            r1.setChecked(false);
            r2.setChecked(false);
        }

        if (r3.isEnabled() && r3.isChecked() ) {
            r3.setChecked(false);
            r4.setChecked(false);
        }

        if (r5.isEnabled() && r5.isChecked() ) {
            r5.setChecked(false);
            r6.setChecked(false);
        }

        if (r7.isEnabled() && r7.isChecked() ) {
            r7.setChecked(false);
            r7.setEnabled(true);
            r8.setChecked(false);
        }

        if (r9.isEnabled() && r9.isChecked() ) {
            r9.setChecked(false);
            r9.setEnabled(true);
            r10.setChecked(false);
        }

        if (r11.isEnabled() && r11.isChecked() ) {
            r11.setChecked(false);
            r11.setEnabled(true);
            r12.setChecked(false);
        }

        if (r13.isEnabled() && r13.isChecked() ) {
            r13.setChecked(false);
            r13.setEnabled(true );
            r14.setChecked(false);
        }

        if (!r1.isEnabled() && r2.isEnabled() && r2.isChecked() ) {
            r2.setChecked(false);
        }

        if (!r3.isEnabled() && r4.isEnabled() && r4.isChecked() ) {
            r4.setChecked(false);
        }

        if (!r5.isEnabled() && r6.isEnabled() && r6.isChecked() ) {
            r6.setChecked(false);
        }

        if (!r7.isEnabled() && r8.isEnabled() && r8.isChecked() ) {
            r8.setChecked(false);
        }

        if (!r9.isEnabled() && r10.isEnabled() && r10.isChecked() ) {
            r10.setChecked(false);
        }

        if (!r11.isEnabled() && r12.isEnabled() && r12.isChecked() ) {
            r12.setChecked(false);
        }

        if (!r13.isEnabled() && r14.isEnabled() && r14.isChecked() ) {
            r14.setChecked(false);
        }

        if (et1.isEnabled()) {
            et1.setText("");
        }

        if (et2.isEnabled()) {
            et2.setText("");
        }

        if (et3.isEnabled()) {
            et3.setText("");
        }

        if (et4.isEnabled()) {
            et4.setText("");
        }

        if (et5.isEnabled()) {
            et5.setText("");
        }

        if (et6.isEnabled()) {
            et6.setText("");
        }

        if (et7.isEnabled()) {
            et7.setText("");
        }
    }

    //Método que permite cambiar a la Activity de historial.
    public void verHistorial(View view) {
        Intent i = new Intent(this, HistorialActivity.class);

        String domingo = et1.getText().toString();
        String lunes = et2.getText().toString();
        String martes = et3.getText().toString();
        String miercoles = et4.getText().toString();
        String jueves = et5.getText().toString();
        String viernes = et6.getText().toString();
        String sabado = et7.getText().toString();

        //Asignación del valor "0" en caso de que el parámtro pasado no tenga declaración previa.
        if (domingo.equals("")) {
            domingo = "0";
        }

        if (lunes.equals("")) {
            lunes = "0";
        }

        if (martes.equals("")) {
            martes = "0";
        }

        if (miercoles.equals("")) {
            miercoles = "0";
        }

        if (jueves.equals("")) {
            jueves = "0";
        }

        if (viernes.equals("")) {
            viernes = "0";
        }

        if (sabado.equals("")) {
            sabado = "0";
        }

        int num1 = Integer.parseInt(domingo);
        int num2 = Integer.parseInt(lunes);
        int num3 = Integer.parseInt(martes);
        int num4 = Integer.parseInt(miercoles);
        int num5 = Integer.parseInt(jueves);
        int num6 = Integer.parseInt(viernes);
        int num7 = Integer.parseInt(sabado);

        if (r1.isChecked()) {
            num1 += 200;
            if (r2.isChecked()) {
                num1 += 100;
            }
        }

        if (r3.isChecked()) {
            num2 += 200;
            if (r4.isChecked()) {
                num2 += 100;
            }
        }

        if (r5.isChecked()) {
            num3 += 200;
            if (r6.isChecked()) {
                num3 += 100;
            }
        }

        if (r7.isChecked()) {
            num4 += 200;
            if (r8.isChecked()) {
                num4 += 100;
            }
        }

        if (r9.isChecked()) {
            num5 += 200;
            if (r10.isChecked()) {
                num5 += 100;
            }
        }

        if (r11.isChecked()) {
            num6 += 200;
            if (r12.isChecked()) {
                num6 += 100;
            }
        }

        if (r13.isChecked()) {
            num7 += 200;
            if (r14.isChecked()) {
                num7 += 100;
            }
        }

        String string1 = String.valueOf(num1);
        String string2 = String.valueOf(num2);
        String string3 = String.valueOf(num3);
        String string4 = String.valueOf(num4);
        String string5 = String.valueOf(num5);
        String string6 = String.valueOf(num6);
        String string7 = String.valueOf(num7);

        i.putExtra("domingo", string1);
        i.putExtra("lunes", string2);
        i.putExtra("martes", string3);
        i.putExtra("miercoles", string4);
        i.putExtra("jueves", string5);
        i.putExtra("viernes", string6);
        i.putExtra("sabado", string7);
        startActivity(i);
    }

    //Método para guardar los datos en la base de datos.
    public void registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String fecha = fechaSeleccionada.getText().toString();
        String caloriasDelDia;
        int calorias = 0;

        String seleccion = spinner1.getSelectedItem().toString();

        if (seleccion.equals("Domingo")) {
            caloriasDelDia = et1.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        } else if (seleccion.equals("Lunes")) {
            caloriasDelDia = et2.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        } else if (seleccion.equals("Martes")) {
            caloriasDelDia = et3.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        } else if (seleccion.equals("Miércoles")) {
            caloriasDelDia = et4.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        } else if (seleccion.equals("Jueves")) {
            caloriasDelDia = et5.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        } else if (seleccion.equals("Viernes")) {
            caloriasDelDia = et6.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        } else if (seleccion.equals("Sábado")) {
            caloriasDelDia = et7.getText().toString();
            calorias = Integer.parseInt(caloriasDelDia);
        }

        ContentValues registro = new ContentValues();
        registro.put("fecha", fecha);
        registro.put("kcal", calorias);

        BaseDeDatos.insert("calorias", null, registro);

        BaseDeDatos.close();
        Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        TextView textView = findViewById(R.id.tv_fecha);
        textView.setText(currentDateString);
    }

}

