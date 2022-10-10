package com.example.a04_creacion_de_elementos_por_codigo;

import android.content.Intent;
import android.os.Bundle;

import com.example.a04_creacion_de_elementos_por_codigo.modelos.Alumno;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.a04_creacion_de_elementos_por_codigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //esto ahorra el findviewById
    private ActivityMainBinding binding;


    //1- CONTENEDOR donde monstrar la info -> Scrool con Linear dentro
    //2- Logica para pintar los elementos -> pintar elemetos
    // 3. Conjunto de datos
    private ArrayList<Alumno> alumnosList;

    private ActivityResultLauncher<Intent> launcherCrearAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launcherCrearAlumnos.launch(new Intent(MainActivity.this,AddAlumnoActivity.class));

            }
        });

        alumnosList = new ArrayList<>();
        inicializaLaunchers();
    }

    private void inicializaLaunchers() {

        launcherCrearAlumnos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData()!= null && result.getData().getExtras() != null){
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                alumnosList.add(alumno);
                                pintarElementos();


                            }
                        }



                    }
                });

    }

    private void pintarElementos() {
        //limpiar elemtos para que no se dupliquen
        binding.content.contenedor.removeAllViews();
        for (Alumno a : alumnosList) {
            TextView txtAlumno = new TextView(MainActivity.this);
            txtAlumno.setText(a.toString());
            binding.content.contenedor.addView(txtAlumno);
        }

    }

}