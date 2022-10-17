package com.example.a04_creacion_de_elementos_por_codigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a04_creacion_de_elementos_por_codigo.databinding.ActivityAddPisoBinding;
import com.example.a04_creacion_de_elementos_por_codigo.modelos.Piso;

public class AddPisoActivity extends AppCompatActivity {

    //1- Activa el binding para la activity
    private ActivityAddPisoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2-Construye el binding
        binding = ActivityAddPisoBinding.inflate(getLayoutInflater());

        //3- Asocia el binding a la activity
        setContentView(binding.getRoot());

        binding.btnCancelarAddPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Piso p = createPiso();
                if (p!=null){

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PISO",p);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();

                } else {
                    Toast.makeText(AddPisoActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Piso createPiso() {

        //comprobar si faltan datos

        if (binding.txtDireccionAddPiso.getText().toString().isEmpty() || binding.txtNumAddPiso.getText().toString().isEmpty() || binding.txtCiudadAddPiso.getText().toString().isEmpty() ||
                binding.txtProvinciaAddPiso.getText().toString().isEmpty() || binding.txtCPAddPiso.getText().toString().isEmpty()){

            return null;

        }


        return new Piso(binding.txtDireccionAddPiso.getText().toString(),Integer.parseInt(binding.txtNumAddPiso.getText().toString()),
                binding.txtCiudadAddPiso.getText().toString(), binding.txtProvinciaAddPiso.getText().toString(),
                binding.txtCPAddPiso.getText().toString(),binding.rbValPisoAddPiso.getRating());



    }
}