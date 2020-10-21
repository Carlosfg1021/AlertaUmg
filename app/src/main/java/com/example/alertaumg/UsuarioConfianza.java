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

    EditText buscar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_usuario_confianza,container,false);
        recyclerViewUsuarios = view.findViewById(R.id.recycleview);

        buscar = (EditText) view.findViewById(R.id.txtbuscar);

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

    private void login(){

        String campo =  buscar.getText().toString().trim();

        if ( !campo.isEmpty()  ){
            try {
                Gson gson = new GsonBuilder().setLenient().create();

                APIUtils.getUsuarioService().obtenerUsuario(campo).enqueue(new Callback<RespuestaAPI<List<Usuario>>> () {
                    @Override
                    public void onResponse(Call<RespuestaAPI<List<Usuario>>> call, Response<RespuestaAPI<List<Usuario>>>  response) {
                        if (response.isSuccessful()) {
                            RespuestaAPI<Usuario> respuesta = response.body();

                            if ( respuesta.getCodigo() == 1 ){
                                Usuario usuario = respuesta.getData();
                                if ( usuario != null ){
                                    //Toast.makeText(Login.this, usuario.toString(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, Contenedor.class);
                                    intent.putExtra("nombre",usuario.getNombre());
                                    intent.putExtra("apellido",usuario.getApellido());
                                    intent.putExtra("direccion",usuario.getDireccion());
                                    intent.putExtra("correo",usuario.getEmail());
                                    intent.putExtra("telefono",usuario.getNumero_telefono());
                                    intent.putExtra("id_usuario",usuario.getId());

                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Usuario encontrado.", Toast.LENGTH_SHORT).show();
                                }
                            }else if ( respuesta.getCodigo() == 0 ){
                                Toast.makeText(getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaAPI<Usuario>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe ingresar correo y contraseña.", Toast.LENGTH_SHORT).show();
        }
    }


}