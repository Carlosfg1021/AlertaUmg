package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alertaumg.Adaptadores.AdaptadorUsuarios;
import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.Inicio.Login;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioConfianza extends Fragment {

    AdaptadorUsuarios adaptadorUsuarios;
    RecyclerView recyclerViewUsuarios ;
    ArrayList<Usuarios> listausuario;

    SearchView buscar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_usuario_confianza,container,false);
        recyclerViewUsuarios = view.findViewById(R.id.recycleview);

        buscar = (SearchView) view.findViewById(R.id.txtbuscar);

        listausuario = new ArrayList<>();
        //cargarlista();

        cargarUsuarios();
        mostraData();

        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listausuario.clear();
                cargarUsuarios();
                mostraData();
                return false;
            }
        });

        /*buscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                listausuario.clear();
                cargarUsuarios();
                mostraData();
                return false;
            }
        });*/



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

    private void cargarUsuarios(){

        String campo =  buscar.getQuery().toString().trim();

        try{

            APIUtils.getUsuarioService().obtenerUsuario(campo).enqueue(new Callback<RespuestaAPI<List<Usuario>>>() {
                @Override
                public void onResponse(Call<RespuestaAPI<List<Usuario>>> call, Response<RespuestaAPI<List<Usuario>>> response) {
                    if (response.isSuccessful()) {
                        RespuestaAPI<List<Usuario>> respuesta = response.body();

                        if ( respuesta.getCodigo() == 1 ){
                            List<Usuario> usuarioLista = respuesta.getData();
                            if ( usuarioLista != null ){
                                //Toast.makeText(Login.this, usuario.toString(), Toast.LENGTH_SHORT).show();
                                listausuario.clear();//Limpiamos antes de cada búsqueda
                                for(int i=0; i<usuarioLista.size();i++){
                                    listausuario.add(new Usuarios(usuarioLista.get(i).getNombre() + " "+usuarioLista.get(i).getApellido(),usuarioLista.get(i).getDireccion()));
                                }

                            }else{

                            }
                        }else if ( respuesta.getCodigo() == 0 ){
                            Toast.makeText(getActivity().getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RespuestaAPI<List<Usuario>>> call, Throwable t) {

                }
            });

        }catch(Exception e){

        }

    }



}