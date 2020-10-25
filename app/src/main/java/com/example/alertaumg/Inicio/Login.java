package com.example.alertaumg.Inicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alertaumg.Contenedor;
import com.example.alertaumg.Funcionalidad.RegistroUsuario;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.R;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    com.google.android.gms.common.SignInButton btnLoginGoogle;
    private String  nombre, apellido,email;
    private Uri foto;
    String urlFoto2;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reconocerObjetos();//Reconocemos objetos del xml (botones..etc)
        recordarUsuario();

        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnLoginGoogle:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void recordarUsuario() {
        // comprobamos si el usuario ya se logueo anteriormente
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias_sistema_alerta", Context.MODE_PRIVATE);
        boolean hay_usuario_logueado= sharedPreferences.getBoolean("usuario_logueado", false);

        if ( hay_usuario_logueado == true ){
            Gson gson = new Gson();
            String json = sharedPreferences.getString("usuario_json", "");
            Usuario usuario = gson.fromJson(json, Usuario.class);

            // Toast.makeText(MainActivity.this, usuario_recuperado.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Login.this, Contenedor.class);
            // pasar datos a la otra activity

            intent.putExtra("nombre",usuario.getNombre());
            intent.putExtra("apellido",usuario.getApellido());
            intent.putExtra("direccion",usuario.getDireccion());
            intent.putExtra("correo",usuario.getEmail());
            intent.putExtra("telefono",usuario.getNumero_telefono());
            intent.putExtra("id_usuario",usuario.getId());
            intent.putExtra("fotografia",usuario.getFotografia());


            startActivity(intent);
            finish(); // cerrar el ciclo de vida de esta activity
        }
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


                                    SharedPreferences sharedPreferences = getSharedPreferences("preferencias_sistema_alerta", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("usuario_logueado", true);
                                    // podemos guardar objetos usando la libreria GSON
                                    Gson gson_preferences = new Gson();
                                    String json_usuario = gson_preferences.toJson(usuario);
                                    editor.putString("usuario_json", json_usuario);
                                    editor.commit(); // commit para síncrono o usar apply() para asícrono






                                    //Toast.makeText(Login.this, usuario.toString(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, Contenedor.class);
                                    intent.putExtra("nombre",usuario.getNombre());
                                    intent.putExtra("apellido",usuario.getApellido());
                                    intent.putExtra("direccion",usuario.getDireccion());
                                    intent.putExtra("correo",usuario.getEmail());
                                    intent.putExtra("telefono",usuario.getNumero_telefono());
                                    intent.putExtra("id_usuario",usuario.getId());
                                    intent.putExtra("fotografia",usuario.getFotografia());
                                    //intent.putExtra("usuario_json", (Parcelable) usuario); //recordamos el login


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
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
             urlFoto2 = account.getPhotoUrl().toString();

            Toast.makeText(getApplicationContext(),account.getEmail(),Toast.LENGTH_SHORT).show();
            signOut();//Cerramos sesión
            Intent intent = new Intent(getApplicationContext(),RegistroUsuario.class);

            intent.putExtra("correo",account.getEmail());
            intent.putExtra("nombre",account.getGivenName());
            intent.putExtra("apellido",account.getFamilyName());
            intent.putExtra("fotografia",urlFoto2);

            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}