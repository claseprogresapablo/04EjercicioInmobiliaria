package com.example.a04_creacion_de_elementos_por_codigo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import com.example.a04_creacion_de_elementos_por_codigo.databinding.ActivityMainBinding;
import com.example.a04_creacion_de_elementos_por_codigo.modelos.Piso;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //esto ahorra el findviewById
    private ActivityMainBinding binding;


    //1- CONTENEDOR donde monstrar la info -> Scrool con Linear dentro
    //2- Logica para pintar los elementos -> pintar elemetos
    //3- Conjunto de datos
    //4-plantilla para mostrar datos
    private ArrayList<Piso> PisosList;

    private ActivityResultLauncher<Intent> launcherCrearPisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: lanzar la creacion de un nuevo objeto
                launcherCrearPisos.launch(new Intent(MainActivity.this, AddPisoActivity.class));

            }
        });

        PisosList = new ArrayList<>();
        inicializaLaunchers();
    }

    private void inicializaLaunchers() {

        launcherCrearPisos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData()!= null && result.getData().getExtras() != null){
                                Piso p = (Piso) result.getData().getExtras().getSerializable("PISO");
                                PisosList.add(p);

                                //Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();
                                pintarElementos();


                            }
                        }



                    }
                });

    }

    private void pintarElementos() {
        //limpiar elemtos para que no se dupliquen
        binding.content.contenedor.removeAllViews();
        for (Piso p : PisosList) {

            //layaut inflater parta leer el xml

            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View pisoView = inflater.inflate(R.layout.piso_model_view, null);

            //View alumnoView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alumno_model_view, null);
            TextView lblCalle = pisoView.findViewById(R.id.lblCallePisoView);
            TextView lblNumero = pisoView.findViewById(R.id.lblNumeroPisoView);
            TextView lblProvincia = pisoView.findViewById(R.id.lblProvinciaPisoView);
            RatingBar rbVal = pisoView.findViewById(R.id.rbValPisoView);

            Toast.makeText(MainActivity.this, p.toString(), Toast.LENGTH_SHORT).show();



            lblCalle.setText(p.getDireccion());
            lblNumero.setText(String.valueOf(p.getNumero()));
            lblProvincia.setText(p.getProvincia());
            rbVal.setRating(p.getValoracion());

            binding.content.contenedor.addView(pisoView);


        }

    }

}