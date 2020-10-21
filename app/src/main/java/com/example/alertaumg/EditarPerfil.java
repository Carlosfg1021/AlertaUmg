package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alertaumg.Funcionalidad.RegistroUsuario;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfil extends AppCompatActivity {

    private EditText txtNombre, txtApellido, txtDireccion,txtTelefono;
    private Spinner spinerDepartamento, spinerMunicipio;
    private Button btnGuardar;
    private int id_usuario;
    private String[] arraySpinerDepartamento;
    private String[] arraySpinerMunicipio;
    private ArrayAdapter<String> adapter;
    private String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        reconocerObjetos();//Reconocemos los objetos del formulario

        id_usuario=getIntent().getExtras().getInt("id_usuario");
        txtNombre.setText(getIntent().getExtras().getString("nombre"));
        txtApellido.setText(getIntent().getExtras().getString("apellido"));
        txtDireccion.setText(getIntent().getExtras().getString("direccion"));
        txtTelefono.setText(getIntent().getExtras().getString("telefono"));
        correo = getIntent().getExtras().getString("correo");

        llenarDepartamento(getIntent().getExtras().getString("departamento"));
        llenarMunicipio(getIntent().getExtras().getString("municipio"));

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();//Editamos datos del usuario
            }
        });

    }

    private void reconocerObjetos(){
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);

        spinerDepartamento = findViewById(R.id.sprDepartamento);
        spinerMunicipio = findViewById(R.id.sprMunicipio);

        btnGuardar = findViewById(R.id.btnGuardar);
    }

    private void editar(){
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String departamento = spinerDepartamento.getSelectedItem().toString();
        String municipio = spinerMunicipio.getSelectedItem().toString();
        String direccion = txtDireccion.getText().toString();

        String nuevaDireccion = departamento +", "+municipio+", "+direccion;

        String telefono = txtTelefono.getText().toString();
        String contrasenia="";
        String contrasenia_confirm="";

        if(!nombre.isEmpty() && !apellido.isEmpty() && !departamento.isEmpty() && !municipio.isEmpty()
        && !direccion.isEmpty() && !telefono.isEmpty()) {

            try{

                Gson gson = new GsonBuilder().setLenient().create();
                APIUtils.getUsuarioService().actualizarUsuario(id_usuario,nombre,apellido,telefono,correo,contrasenia,contrasenia_confirm,nuevaDireccion).enqueue(new Callback<RespuestaAPI<String>>() {
                    @Override
                    public void onResponse(Call<RespuestaAPI<String>> call, Response<RespuestaAPI<String>> response) {
                        if (response.isSuccessful()) {
                            RespuestaAPI<String> respuesta = response.body();

                            if ( respuesta.getCodigo() == 1 ){
                                String objeto = respuesta.getData();
                                if ( objeto != null ){
                                    Toast.makeText(EditarPerfil.this, respuesta.toString(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(),Contenedor.class);

                                    intent.putExtra("nombre",nombre);
                                    intent.putExtra("apellido",apellido);
                                    intent.putExtra("direccion",direccion);
                                    intent.putExtra("correo",correo);
                                    intent.putExtra("telefono",telefono);
                                    intent.putExtra("id_usuario",id_usuario);

                                    startActivity(intent);
                                    finish();

                                }else{
                                    Toast.makeText(getApplicationContext(), "¡Datos actualizados!", Toast.LENGTH_SHORT).show();
                                }
                            }else if ( respuesta.getCodigo() == 0 ){

                                Toast.makeText(getApplicationContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaAPI<String>> call, Throwable t) {

                    }
                });

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Verifica los campos",Toast.LENGTH_SHORT).show();
        }
    }

    private void llenarDepartamento(String nombre){
        arraySpinerDepartamento = new String[]{nombre};

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinerDepartamento);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerDepartamento.setAdapter(adapter);
    }

    private void llenarMunicipio(String nombre){
        arraySpinerMunicipio = new String[]{nombre};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraySpinerMunicipio);
        spinerMunicipio.setAdapter(adapter);
    }
}
