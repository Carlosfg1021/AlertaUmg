package com.example.alertaumg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.UsuarioConfianza;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguirUsuario extends AppCompatActivity {

    TextView lblNombre, lblApellido, lblDepartamento,lblMunicipio,lblDireccion, lblTelefono, lblNombreEncabezado;
    private Button btnSeguir;
    ImageView imgPerfilSeguir;
    int id_confianza;
    int mi_id;
    List<UsuarioConfianza> listaC;
    private boolean resultado;

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
        imgPerfilSeguir = findViewById(R.id.EditarImgPerfil);
        listaC = new ArrayList<>();

        String urlFotoEditar = getIntent().getExtras().getString(("fotografia"));

        //Toast.makeText(this, urlFotoMenu, Toast.LENGTH_SHORT).show();

        try{
            Glide.with(getApplicationContext())
                    .load(urlFotoEditar)
                    .into((ImageView) imgPerfilSeguir);
            //Comentario
        }catch (Exception e){

        }

        id_confianza =getIntent().getExtras().getInt("id_confianza");
        mi_id = getIntent().getExtras().getInt("id_usuario");
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

        comprobarSeguido();

    }

    public void comprobarSeguido(){

        try{
            Gson gson = new GsonBuilder().setLenient().create();
            APIUtils.getUsuarioConfianzaService().obtenerUsuariosConfianza(mi_id).enqueue(new Callback<RespuestaAPI<List<UsuarioConfianza>>>() {
                @Override
                public void onResponse(Call<RespuestaAPI<List<UsuarioConfianza>>> call, Response<RespuestaAPI<List<UsuarioConfianza>>> response) {
                    if (response.isSuccessful()) {
                        RespuestaAPI<List<UsuarioConfianza>> respuesta = response.body();

                        if ( respuesta.getCodigo() == 1 ){
                            List<UsuarioConfianza> usSeguir = respuesta.getData();
                            for(int i=0; i<usSeguir.size();i++){
                                if(usSeguir.get(i).getId()== id_confianza){
                                    btnSeguir.setEnabled(false);
                                    btnSeguir.setText("Seguido");
                                }
                            }


                        }else if ( respuesta.getCodigo() == 0 ){
                            Toast.makeText(SeguirUsuario.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                            btnSeguir.setEnabled(true);
                            btnSeguir.setText("SEGUIR");
                        }
                    }else{
                        Toast.makeText(SeguirUsuario.this, "Error en el servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RespuestaAPI<List<UsuarioConfianza>>> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }


    }


}