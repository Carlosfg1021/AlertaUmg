package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import com.example.alertaumg.Adaptadores.AdaptadorContacto;
import com.example.alertaumg.Adaptadores.AdaptadorNotificacion;
import com.example.alertaumg.Entidades.ConctactosU;
import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.ContactosEmergencia;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.Utilidades.APIUtils;
import com.example.alertaumg.Utilidades.Servicios.ContactoEmergenciaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactosEmergenciaActivity extends AppCompatActivity {


   List<ConctactosU> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_emergencia);

elements=new ArrayList<>();
        //init();

            try{
                APIUtils.getContactoEmergenciaService().obtenerContactosEmergencia().enqueue(new Callback<RespuestaAPI<List<ContactosEmergencia>>>() {
                    @Override
                    public void onResponse(Call<RespuestaAPI<List<ContactosEmergencia>>> call, Response<RespuestaAPI<List<ContactosEmergencia>>> response) {
                        if (response.isSuccessful()) {
                            RespuestaAPI<List<ContactosEmergencia>> respuesta = response.body();

                            if ( respuesta.getCodigo() == 1 ){
                                List<ContactosEmergencia> contEmer = respuesta.getData();
                                if ( contEmer != null ){
                                    //Toast.makeText(Login.this, usuario.toString(), Toast.LENGTH_SHORT).show();
                                    elements.clear();//Limpiamos antes de cada búsqueda
                                    String numerosContacto="";
                                    for(int i=0; i<contEmer.size();i++){
                                        for(int r=0; r<contEmer.get(i).getTelefonos().size(); r++){
                                            numerosContacto = numerosContacto+"\n"+contEmer.get(i).getTelefonos().get(r).getNumero_telefono();
                                        }
                                        elements.add(new ConctactosU(contEmer.get(i).getNombre(),numerosContacto));
                                    }
                                    init();
                                }else{

                                }
                            }else if ( respuesta.getCodigo() == 0 ){
                                Toast.makeText(getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaAPI<List<ContactosEmergencia>>> call, Throwable t) {

                    }
                });
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

    }


    public void init(){

        //elements = new ArrayList<>();
        //elements.add(new ConctactosU("Bomberos Vluntarios","79331103"));
        AdaptadorContacto adaptadorContacto = new AdaptadorContacto(elements,this);
        RecyclerView recyclerView = findViewById(R.id.recycleview_contactos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //-------------------eventos-----------------

        adaptadorContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Seleccionado: "+elements.get(recyclerView.getChildAdapterPosition(view)).getNombre(),Toast.LENGTH_SHORT).show();

                //-----------AQUÍ MANDAMOS A LLAMAR AL ACTIVITY QUE NOS MOSTRARÁ LOS DETALLES DE LA TIENDA


            }
        });


        recyclerView.setAdapter(adaptadorContacto);
    }



}