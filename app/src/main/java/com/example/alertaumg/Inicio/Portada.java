package com.example.alertaumg.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.alertaumg.Funcionalidad.EmitirAlerta;
import com.example.alertaumg.R;

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        //startService(new Intent(getApplicationContext(), BackgroundService.class));//Iniciamos servicio

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            //Iniciamos servicios en segundo plano.
                //closeBackgroundProcess();//Cerramos servicios en segundo plano para que se reinicien.

               // startService(new Intent(getApplicationContext(), BackgroundService.class));//Iniciamos servicio

                Intent intent = new Intent(Portada.this, Login.class);
                startActivity(intent);

            }
        }, 2000);
    }

    //Método para detener el servicio en segundo plano de la aplicación

    private void closeBackgroundProcess(){
        stopService(new Intent(getApplicationContext(),BackgroundService.class));
    }

}