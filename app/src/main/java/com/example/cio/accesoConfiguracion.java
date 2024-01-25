package com.example.cio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class accesoConfiguracion extends AppCompatActivity {

    EditText txtClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_configuracion);

        txtClave = findViewById(R.id.txtClave);
    }

    public void onclickIngresar(View view) {
        if (txtClave.getText().toString().equals("295455292")){
            Intent intent = new Intent(accesoConfiguracion.this, Configuraciones.class);
            startActivity(intent);
        } else {
            txtClave.setText("");
            Toast.makeText(this, "Clave incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
}