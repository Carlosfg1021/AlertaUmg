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

import com.example.alertaumg.Adaptadores.AdaptadorUsuarios;
import com.example.alertaumg.Entidades.Usuarios;

import java.util.ArrayList;

public class UsuarioConfianza extends Fragment {

    AdaptadorUsuarios adaptadorUsuarios;
    RecyclerView recyclerViewUsuarios ;
    ArrayList<Usuarios> listausuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_usuario_confianza,container,false);
        recyclerViewUsuarios = view.findViewById(R.id.recycleview);

        listausuario = new ArrayList<>();
        cargarlista();
        mostraData();

        return view;
    }

    public void cargarlista(){

        listausuario.add(new Usuarios("Carlos Franco","Gualan"));
    }

    public void mostraData(){

        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorUsuarios = new AdaptadorUsuarios(getContext(), listausuario);
        recyclerViewUsuarios.setAdapter(adaptadorUsuarios);

    }


}