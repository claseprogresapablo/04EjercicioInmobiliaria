package com.example.a04_creacion_de_elementos_por_codigo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a04_creacion_de_elementos_por_codigo.databinding.ActivityAddAlumnoBinding;
import com.example.a04_creacion_de_elementos_por_codigo.modelos.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {

    //1- Activa el binding para la activity
    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2-Construye el binding
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());

        //3- Asocia el binding a la activity
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alumno a = createAlumno();
                if (a!=null){

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO",a);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();

                } else {
                    Toast.makeText(AddAlumnoActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Alumno createAlumno() {


        if (binding.txtNombreAddAlumno.getText().toString().isEmpty() || binding.txtApellidosAddAlumno.getText().toString().isEmpty())
            return null;
        if (binding.spCiclosAddAlumno.getSelectedItemPosition() == 0){
            return null;
        }
        if (!binding.rbGrupoAAddAlumno.isChecked() && !binding.rbGrupoBAddAlumno.isChecked() && !binding.rbGrupoCAddAlumno.isChecked()){
            return null;
        }


        String ciclo = (String) binding.spCiclosAddAlumno.getSelectedItem();
        //devuelve el id del seleccionado y nos creamos una variable radioButton para poder acceder a su texto
        RadioButton rb = findViewById(binding.rgGruposAddAlumno.getCheckedRadioButtonId());
        char grupo = rb.getText().charAt(0);

        return new Alumno(binding.txtNombreAddAlumno.getText().toString(), binding.txtApellidosAddAlumno.getText().toString(),ciclo,grupo);
    }
}