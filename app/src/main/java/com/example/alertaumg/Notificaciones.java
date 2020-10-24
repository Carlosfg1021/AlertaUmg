package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alertaumg.Adaptadores.AdaptadorNotificacion;
import com.example.alertaumg.Adaptadores.AdaptadorUsuarios;
import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.Inicio.Login;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.TipoAlerta;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Notificaciones extends Fragment {

    AdaptadorNotificacion adaptadorNotificacion;
    RecyclerView recyclerViewNotificacion;
    ArrayList<NotificacionU> listanotificacion;

    TipoAlerta alertaCardView;
    Usuario usuarioCardView;
    Indice indiceUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_notificaciones,container,false);

        recyclerViewNotificacion = view.findViewById(R.id.recycleview_noti);
        listanotificacion = new ArrayList<>();

        //cargarlista();
       // mostraData();

        try{


            Gson gson = new GsonBuilder().setLenient().create();
            APIUtils.getAlertaService().obtenerAlertasUsuario(getActivity().getIntent().getExtras().getInt("id_usuario")).enqueue(new Callback<RespuestaAPI<List<Alerta>>>() {
                @Override
                public void onResponse(Call<RespuestaAPI<List<Alerta>>> call, Response<RespuestaAPI<List<Alerta>>> response) {

                    if (response.isSuccessful()) {
                        RespuestaAPI<List<Alerta>> respuesta = response.body();

                        if ( respuesta.getCodigo() == 1 ){
                            List<Alerta> alertas = respuesta.getData();
                            if (alertas != null ){


                                //Ya que obtenemos alerta por alerta será necesario obtener la descripción
                                //del tipo de alerta, por lo que deberemos hacer una subconsulta a la API tipoAlerta
                                //para obtener el tipo de alerta a través del id

                                //Llenamos las notificaciones
                                listanotificacion.clear();
                                for(int i=0;i<alertas.size();i++) {
                                    indiceUsuario.setIndice(i);
                                    //--------------------------------Obtenemos el tipo de alerta-------------------------------

                                    try {
                                    APIUtils.getTipoAlertaService().obtenerAlertaPorId(alertas.get(i).getId_tipo_alerta()).enqueue(new Callback<RespuestaAPI<TipoAlerta>>() {
                                        @Override
                                        public void onResponse(Call<RespuestaAPI<TipoAlerta>> call, Response<RespuestaAPI<TipoAlerta>> response) {
                                                if(response.isSuccessful()){
                                                    RespuestaAPI<TipoAlerta> resAlert = response.body();

                                                    if ( resAlert.getCodigo() == 1 ){
                                                        TipoAlerta alert = resAlert.getData();

                                                        if(alert!=null){

                                                            //Llenamos variable de tipo de alerta para posteriormente cargar descripción en el cardview
                                                            alertaCardView.setDescripcion(alert.getDescripcion());
                                                            alertaCardView.setId(alert.getId());
                                                            alertaCardView.setEstado(alert.getEstado());

                                                        }else{
                                                            //Toast.makeText(view.getContext(), "Tipo de alerta encontrada.", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }else if ( resAlert.getCodigo() == 0 ){
                                                        Toast.makeText(view.getContext(), resAlert.getMensaje(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }else{
                                                    Toast.makeText(view.getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                                                }
                                        }

                                        @Override
                                        public void onFailure(Call<RespuestaAPI<TipoAlerta>> call, Throwable t) {

                                        }
                                    });

                                }catch(Exception e){
                                        Toast.makeText(view.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                         //------------------------------OBTENEMOS EL NOMBRE DE USUARIO QUE EMITIÓ LA ALERTA----------

                                    try{
                                        APIUtils.getUsuarioService().obtenerTodos().enqueue(new Callback<RespuestaAPI<ArrayList<Usuario>>>() {
                                            @Override
                                            public void onResponse(Call<RespuestaAPI<ArrayList<Usuario>>> call, Response<RespuestaAPI<ArrayList<Usuario>>> response) {
                                                if(response.isSuccessful()){
                                                    RespuestaAPI<ArrayList<Usuario>> resUs = response.body();

                                                    if ( resUs.getCodigo() == 1 ){
                                                        ArrayList<Usuario> us = resUs.getData();

                                                        if(us!=null){

                                                            //Llenamos variable de tipo de alerta para posteriormente cargar descripción en el cardview

                                                            for(int u=0; u<us.size();u++){
                                                                if(us.get(u).getId()==alertas.get(indiceUsuario.getIndice()).getId_usuario()){
                                                                    usuarioCardView=us.get(u);//Encontramos el usuario
                                                                }
                                                            }

                                                        }else{
                                                            //Toast.makeText(view.getContext(), "Tipo de alerta encontrada.", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }else if ( resUs.getCodigo() == 0 ){
                                                        Toast.makeText(view.getContext(), resUs.getMensaje(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }else{
                                                    Toast.makeText(view.getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<RespuestaAPI<ArrayList<Usuario>>> call, Throwable t) {

                                            }
                                        });
                                    }catch (Exception e){

                                    }

                                    //----------Solo queda añadir a la lista los datos----------------------------------------
                                    listanotificacion.add(new NotificacionU(usuarioCardView.getNombre()+" "+usuarioCardView.getApellido(), alertaCardView.getDescripcion()));
                                }
                                mostraData();
                            }else{
                                Toast.makeText(view.getContext(), "Alertas encontradas.", Toast.LENGTH_SHORT).show();
                            }
                        }else if ( respuesta.getCodigo() == 0 ){
                            Toast.makeText(view.getContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(view.getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<RespuestaAPI<List<Alerta>>> call, Throwable t) {

                }
            });

        }catch(Exception e){
            Toast.makeText(view.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        return view;

    }
/*
    public void cargarlista(){

        listanotificacion.add(new NotificacionU("Carlos Franco","Violencia de Genero"));
    }
*/
    public void mostraData(){

        recyclerViewNotificacion.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorNotificacion = new AdaptadorNotificacion(getContext(),listanotificacion);
        recyclerViewNotificacion.setAdapter(adaptadorNotificacion);


    }




}