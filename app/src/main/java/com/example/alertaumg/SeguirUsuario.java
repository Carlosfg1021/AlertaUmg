package com.example.alertaumg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguirUsuario extends AppCompatActivity {

    TextView lblNombre, lblApellido, lblDepartamento,lblMunicipio,lblDireccion, lblTelefono, lblNombreEncabezado;
    private Button btnSeguir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguir_usuario);
        btnSeguir = findViewById(R.id.btnSeguir);
        lblNombre=findViewById(R.id.lblNombre);
        lblApellido = findViewById(R.id.lblApellido);
        lblDepartamento = findViewById(R.id.lblDepartamento);
        lblMunicipio = findViewById(R.id.lblMunicipio);
        lblDireccion = findViewById(R.id.lblDireccion);
        lblTelefono = findViewById(R.id.lblTelefono);
        lblNombreEncabezado = findViewById(R.id.personaNombreSeguir);

        int id_confianza =getIntent().getExtras().getInt("id_confianza");
        int mi_id = getIntent().getExtras().getInt("id_usuario");
        lblNombre.setText(getIntent().getExtras().getString("nombre_confianza"));
        lblApellido.setText(getIntent().getExtras().getString("apellido_confianza"));
        lblNombreEncabezado.setText(lblNombre.getText().toString()+" "+lblApellido.getText().toString());
        lblTelefono.setText(getIntent().getExtras().getString("telefono_confianza"));
        String dir = getIntent().getExtras().getString("direccion_confianza");
        String departamento="";
        String municipio="";
        String direccion="";
        int comas=0;
        char coma = ',';

        for(int i=0; i<dir.length(); i++){
            char letra = dir.charAt(i);

            if(letra == coma){
                comas++;
            }

            if(comas==0 && letra!=coma){
                departamento = departamento + dir.charAt(i);
            }

            if(comas==1 && letra!=coma){
                municipio = municipio + dir.charAt(i);
            }

            if(comas==2 && letra!=coma){
                direccion = direccion + dir.charAt(i);
            }

        }

        lblDepartamento.setText(departamento);
        lblMunicipio.setText(municipio);
        lblDireccion.setText(direccion);

        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Gson gson = new GsonBuilder().setLenient().create();
                    APIUtils.getUsuarioConfianzaService().registrarUsuarioConfianza(mi_id,id_confianza).enqueue(new Callback<RespuestaAPI<String>>() {
                        @Override
                        public void onResponse(Call<RespuestaAPI<String>> call, Response<RespuestaAPI<String>> response) {
                            if (response.isSuccessful()) {
                                RespuestaAPI<String> respuesta = response.body();

                                if ( respuesta.getCodigo() == 1 ){

                                    Toast.makeText(view.getContext().getApplicationContext(),"Contacto de emergencia agregado",Toast.LENGTH_SHORT).show();

                                }else if ( respuesta.getCodigo() == 0 ){
                                    Toast.makeText(view.getContext().getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(view.getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaAPI<String>> call, Throwable t) {

                        }
                    });

                }catch (Exception e){

                }

            }
        });



    }
}