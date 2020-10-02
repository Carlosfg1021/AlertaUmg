package com.example.alertaumg.Funcionalidad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.alertaumg.R;

public class RegistroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        getSupportActionBar().hide();
    }
}