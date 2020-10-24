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
import android.widget.Button;
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
    Button btnSeguir;
    SearchView buscar;

    private int user_confianza_global_id;
    private int my_user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_usuario_confianza,container,false);
        recyclerViewUsuarios = view.findViewById(R.id.recycleview);

        buscar = (SearchView) view.findViewById(R.id.txtbuscar);


        listausuario = new ArrayList<>();
        //cargarlista();
        //cargarTodos();
        cargarUsuarios();
        //mostraData();



        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listausuario.clear();
                cargarUsuarios();
                //mostraData();
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

//        listausuario.add(new Usuarios("Carlos Franco","Gualan",1,1));
    }

    public void mostraData(){

        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorUsuarios = new AdaptadorUsuarios(getContext(),listausuario);
        adaptadorUsuarios.miId = my_user_id;
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
                                Usuario usEnviar;
                                for(int i=0; i<usuarioLista.size();i++){

                                    String dir = usuarioLista.get(i).getDireccion();
                                    String departamento="";
                                    String municipio="";
                                    String direccion="";
                                    int comas=0;
                                    char coma = ',';

                                    for(int a=0; a<dir.length(); a++){
                                        char letra = dir.charAt(a);

                                        if(letra == coma){
                                            comas++;
                                        }

                                        if(comas==0 && letra!=coma){
                                            departamento = departamento + dir.charAt(a);
                                        }

                                        if(comas==1 && letra!=coma){
                                            municipio = municipio + dir.charAt(a);
                                        }

                                        if(comas==2 && letra!=coma){
                                            direccion = direccion + dir.charAt(a);
                                        }

                                    }

                                    usEnviar = usuarioLista.get(i);
                                    listausuario.add(new Usuarios(usuarioLista.get(i).getNombre() + " "+usuarioLista.get(i).getApellido(),departamento,usEnviar));
                                    //user_confianza_global_id=usuarioLista.get(i).getId();//id de usuario de confianza
                                    my_user_id = getActivity().getIntent().getExtras().getInt("id_usuario");//mi id

                                    //Toast.makeText(getActivity().getApplicationContext(),listausuario.get(i).getNombre(),Toast.LENGTH_SHORT).show();
                                }
                                mostraData();
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


    private void cargarTodos(){
            try{

                APIUtils.getUsuarioService().obtenerTodos().enqueue(new Callback<RespuestaAPI<ArrayList<Usuario>>>() {
                    @Override
                    public void onResponse(Call<RespuestaAPI<ArrayList<Usuario>>> call, Response<RespuestaAPI<ArrayList<Usuario>>> response) {
                        if (response.isSuccessful()) {
                            RespuestaAPI<ArrayList<Usuario>> respuesta = response.body();

                            if ( respuesta.getCodigo() == 1 ){
                                ArrayList<Usuario> usuarioLista = respuesta.getData();
                                if ( usuarioLista != null ){
                                    //Toast.makeText(Login.this, usuario.toString(), Toast.LENGTH_SHORT).show();
                                    listausuario.clear();//Limpiamos antes de cada búsqueda
                                    for(int i=0; i<usuarioLista.size();i++){

//                                        listausuario.add(new Usuarios((usuarioLista.get(i).getNombre() + " "+usuarioLista.get(i).getApellido(),departamento,getActivity().getIntent().getExtras().getInt("id_usuario"),1));

                                    }

                                    //mostraData();

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
                    public void onFailure(Call<RespuestaAPI<ArrayList<Usuario>>> call, Throwable t) {

                    }
                });

            }catch(Exception e){

        }
    }


}