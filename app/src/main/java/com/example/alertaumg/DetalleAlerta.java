package com.example.alertaumg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleAlerta extends AppCompatActivity {

    private Button btnVerEnMapa, btnMarcarComoVista;
    private TextView txtTipoAlerta, txtNombreEmisor, txtDescripcion, txtLatitud, txtLongitud;
    private int id_alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alerta);

        reconocerObjetos();

        id_alerta = getIntent().getExtras().getInt("id_alerta");

        txtTipoAlerta.setText(getIntent().getExtras().getString("tipo_alerta"));
        txtNombreEmisor.setText(getIntent().getExtras().getString("emisor"));
        txtDescripcion.setText(getIntent().getExtras().getString("descripcion"));
        txtLatitud.setText(getIntent().getExtras().getString("latitud"));
        txtLongitud.setText(getIntent().getExtras().getString("longitud"));

        btnVerEnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(txtLongitud.getText().toString().equals("")==false && txtLatitud.getText().toString().equals("")==false) {
                  Uri gmmIntentUri = Uri.parse("google.navigation:q=" + txtLatitud.getText().toString() + "," + txtLongitud.getText().toString() + "&mode=d");
                  Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                  mapIntent.setPackage("com.google.android.apps.maps");
                  Toast.makeText(getApplicationContext(),"Localizando...",Toast.LENGTH_SHORT).show();
                  startActivity(mapIntent);
              }else{
                  Toast.makeText(getApplicationContext(),"No hay ubicación que mostrar",Toast.LENGTH_SHORT).show();
              }
            }
        });

        btnMarcarComoVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void reconocerObjetos(){
        txtTipoAlerta = findViewById(R.id.lblTipoAlerta);
        txtNombreEmisor = findViewById(R.id.lblEmisor);
        txtDescripcion = findViewById(R.id.lblDescripcion);
        txtLatitud = findViewById(R.id.lblLatitud);
        txtLongitud = findViewById(R.id.lblLongitud);

        btnVerEnMapa = findViewById(R.id.btnVerEnMapa);
        btnMarcarComoVista = findViewById(R.id.btnMarcarComoVista);
    }
}