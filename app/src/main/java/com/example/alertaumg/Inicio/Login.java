package com.example.alertaumg.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alertaumg.Contenedor;
import com.example.alertaumg.R;

public class Login extends AppCompatActivity {
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reconocerObjetos();//Reconocemos objetos del xml (botones..etc)

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Contenedor.class);
                startActivity(intent);
            }
        });

    }

    private void reconocerObjetos(){
        btnLogin = findViewById(R.id.btnLogin);
    }
}