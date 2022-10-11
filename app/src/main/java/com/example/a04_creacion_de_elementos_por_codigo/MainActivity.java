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

import android.view.LayoutInflater;
import android.view.View;

import com.example.a04_creacion_de_elementos_por_codigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //esto ahorra el findviewById
    private ActivityMainBinding binding;


    //1- CONTENEDOR donde monstrar la info -> Scrool con Linear dentro
    //2- Logica para pintar los elementos -> pintar elemetos
    //3- Conjunto de datos
    //4-plantilla para mostrar datos
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

            //layaut inflater parta leer el xml

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View alumnoView = inflater.inflate(R.layout.alumno_model_view, null);

            //View alumnoView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alumno_model_view, null);
            TextView lblNombre = alumnoView.findViewById(R.id.lblNombreAlumnoView);
            TextView lblApellidos = alumnoView.findViewById(R.id.lblApellidosAlumnoView);
            TextView lblCiclo = alumnoView.findViewById(R.id.lblCicloAlumnoView);
            TextView lblGrupo = alumnoView.findViewById(R.id.lblGrupoAlumnoView);

            lblNombre.setText(a.getNombre());
            lblApellidos.setText(a.getApellidos());
            lblCiclo.setText(a.getCiclo());
            lblGrupo.setText(String.valueOf(a.getGrupo()));

            binding.content.contenedor.addView(alumnoView);


        }

    }

}