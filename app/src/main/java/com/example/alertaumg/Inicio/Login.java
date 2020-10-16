package com.example.alertaumg.Inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alertaumg.Contenedor;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.R;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText txtcorreo, txtpassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reconocerObjetos();//Reconocemos objetos del xml (botones..etc)

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        String email    =   txtcorreo.getText().toString().trim();
        String password =   txtpassword.getText().toString().trim();

        if ( !email.isEmpty() && !password.isEmpty() ){
            try {
                Gson gson = new GsonBuilder().setLenient().create();

                APIUtils.getUsuarioService().login(email, password).enqueue(new Callback<RespuestaAPI<Usuario>>() {
                    @Override
                    public void onResponse(Call<RespuestaAPI<Usuario>> call, Response<RespuestaAPI<Usuario>> response) {
                        if (response.isSuccessful()) {
                            RespuestaAPI<Usuario> respuesta = response.body();

                            if ( respuesta.getCodigo() == 1 ){
                                Usuario usuario = respuesta.getData();
                                if ( usuario != null ){
                                    //Toast.makeText(Login.this, usuario.toString(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, Contenedor.class);
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

    private void reconocerObjetos(){
        btnLogin = findViewById(R.id.btnLogin);
        txtcorreo = findViewById(R.id.txtcorreo);
        txtpassword = findViewById(R.id.txtpassword);
    }
}