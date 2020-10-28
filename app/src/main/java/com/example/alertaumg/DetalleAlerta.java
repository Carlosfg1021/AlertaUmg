package com.example.alertaumg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertaumg.Funcionalidad.RegistroUsuario;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleAlerta extends AppCompatActivity {

    private Button btnVerEnMapa, btnMarcarComoVista;
    private TextView txtTipoAlerta, txtNombreEmisor, txtDescripcion, txtLatitud, txtLongitud;
    private int id_alerta;
    private int id_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alerta);

        reconocerObjetos();

        id_alerta = getIntent().getExtras().getInt("id_alerta");
        id_usuario = getIntent().getExtras().getInt("id_usuario");
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
                try{
                    Gson gson = new GsonBuilder().setLenient().create();
                    APIUtils.getAlertaService().marcarAlertaVista(id_alerta,id_usuario).enqueue(new Callback<RespuestaAPI<Alerta>>() {
                        @Override
                        public void onResponse(Call<RespuestaAPI<Alerta>> call, Response<RespuestaAPI<Alerta>> response) {
                            if (response.isSuccessful()) {
                                RespuestaAPI<Alerta> respuesta = response.body();

                                if ( respuesta.getCodigo() == 1 ){
                                    Alerta objeto = respuesta.getData();
                                    if ( objeto != null ){

                                        Toast.makeText(DetalleAlerta.this, respuesta.toString(), Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(), "¡Alerta marcada como vista!", Toast.LENGTH_SHORT).show();
                                    }
                                }else if ( respuesta.getCodigo() == 0 ){

                                    Toast.makeText(getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaAPI<Alerta>> call, Throwable t) {

                        }
                    });
                }catch (Exception e){

                }
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