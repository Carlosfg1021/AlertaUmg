package com.example.alertaumg.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.alertaumg.BotonPanico;
import com.example.alertaumg.Funcionalidad.RegistroUsuario;
import com.example.alertaumg.R;
import com.example.alertaumg.TipoAlerta;

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Portada.this, Login.class);
                startActivity(intent);
            }
        }, 2000);
    }
}