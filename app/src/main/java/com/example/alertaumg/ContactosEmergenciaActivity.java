package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import com.example.alertaumg.Adaptadores.AdaptadorContacto;
import com.example.alertaumg.Adaptadores.AdaptadorNotificacion;
import com.example.alertaumg.Entidades.ConctactosU;
import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.ContactosEmergencia;
import com.example.alertaumg.Modelos.RespuestaAPI;
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
            Gson gson = new GsonBuilder().setLenient().create();
            APIUtils.getContactoEmergenciaService().obtenerContactosEmergencia().enqueue(new Callback<RespuestaAPI<List<ContactosEmergencia>>>() {
                @Override
                public void onResponse(Call<RespuestaAPI<List<ContactosEmergencia>>> call, Response<RespuestaAPI<List<ContactosEmergencia>>> response) {
                    if (response.isSuccessful()) {
                        RespuestaAPI<List<ContactosEmergencia>> respuesta = response.body();

                        if ( respuesta.getCodigo() == 1 ){
                            List<ContactosEmergencia> contEmerg = respuesta.getData();
                            if (contEmerg != null ){
                                String nombreContactoEmergencia="";
                                String telefonosEmergencia="";
                                elements.clear();//Limpiamos antes de volver a listar

                                for(int i=0; i<contEmerg.size();i++){

                                   for(int a=0; a<contEmerg.get(i).getTelefonos().size(); a++){
                                       telefonosEmergencia= telefonosEmergencia+"\n"+contEmerg.get(i).getTelefonos().get(a);
                                   }

                                   nombreContactoEmergencia = contEmerg.get(i).getNombre();
                                   elements.add(new ConctactosU(nombreContactoEmergencia,telefonosEmergencia));
                                   telefonosEmergencia="";//Limpiamos telefonos para volver a cargar otra lista cuando entremos al ciclo for
                                }

                                init();//Aqui mostramos los resultados

                            }else{
                                Toast.makeText(getApplicationContext(), "Estos son los números de emergencia", Toast.LENGTH_SHORT).show();
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
        recyclerView.setAdapter(adaptadorContacto);
    }



}