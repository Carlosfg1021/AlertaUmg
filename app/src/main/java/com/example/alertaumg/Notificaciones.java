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

           APIUtils.getAlertaService().obtenerAlertasNoVistas(getActivity().getIntent().getExtras().getInt("id_usuario")).enqueue(new Callback<RespuestaAPI<List<Alerta>>>() {
               @Override
               public void onResponse(Call<RespuestaAPI<List<Alerta>>> call, Response<RespuestaAPI<List<Alerta>>> response) {
                   if (response.isSuccessful()) {
                       RespuestaAPI<List<Alerta>> respuesta = response.body();

                       if ( respuesta.getCodigo() == 1 ){
                           List<Alerta> alerta = respuesta.getData();
                           if (alerta != null ){

                               listanotificacion.clear();//Limpiamos antes de volver a listar

                               for(int i=0; i<alerta.size();i++){
                                   listanotificacion.add(new NotificacionU(alerta.get(i).getNombre_usuario(),alerta.get(i).getNombre_tipo_alerta()));
                               }

                               mostraData();
                           }else{
                               Toast.makeText(view.getContext(), "Usuario encontrado.", Toast.LENGTH_SHORT).show();
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

       }catch (Exception e){
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