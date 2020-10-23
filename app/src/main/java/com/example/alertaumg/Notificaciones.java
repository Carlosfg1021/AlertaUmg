package com.example.alertaumg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alertaumg.Adaptadores.AdaptadorNotificacion;
import com.example.alertaumg.Adaptadores.AdaptadorUsuarios;
import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.Entidades.Usuarios;

import java.util.ArrayList;


public class Notificaciones extends Fragment {

    AdaptadorNotificacion adaptadorNotificacion;
    RecyclerView recyclerViewNotificacion;
    ArrayList<NotificacionU> listanotificacion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_notificaciones,container,false);

        recyclerViewNotificacion = view.findViewById(R.id.recycleview_noti);
        listanotificacion = new ArrayList<>();

        cargarlista();
        mostraData();


        return view;

    }

    public void cargarlista(){

        listanotificacion.add(new NotificacionU("Carlos Franco","Violencia de Genero"));
    }

    public void mostraData(){

        recyclerViewNotificacion.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorNotificacion = new AdaptadorNotificacion(getContext(),listanotificacion);
        recyclerViewNotificacion.setAdapter(adaptadorNotificacion);


    }




}