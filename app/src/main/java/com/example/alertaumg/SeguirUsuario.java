package com.example.alertaumg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SeguirUsuario extends AppCompatActivity {

    TextView lblNombre, lblApellido, lblDepartamento,lblMunicipio,lblDireccion, lblTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguir_usuario);

        lblNombre=findViewById(R.id.lblNombre);
        lblApellido = findViewById(R.id.lblApellido);
        lblDepartamento = findViewById(R.id.lblDepartamento);
        lblMunicipio = findViewById(R.id.lblMunicipio);
        lblDireccion = findViewById(R.id.lblDireccion);
        lblTelefono = findViewById(R.id.lblTelefono);

        int id_confianza =getIntent().getExtras().getInt("id_confianza");
        //int mi_id = getIntent().getExtras().getInt("miId");
        lblNombre.setText(getIntent().getExtras().getString("nombre_confianza"));
        lblApellido.setText(getIntent().getExtras().getString("apellido_confianza"));
        lblTelefono.setText(getIntent().getExtras().getString("telefono_confianza"));
        String dir = getIntent().getExtras().getString("direccion_confianza");
        String departamento="";
        String municipio="";
        String direccion="";
        int comas=0;
        char coma = ',';

        for(int i=0; i<dir.length(); i++){
            char letra = dir.charAt(i);

            if(letra == coma){
                comas++;
            }

            if(comas==0 && letra!=coma){
                departamento = departamento + dir.charAt(i);
            }

            if(comas==1 && letra!=coma){
                municipio = municipio + dir.charAt(i);
            }

            if(comas==2 && letra!=coma){
                direccion = direccion + dir.charAt(i);
            }

        }

        lblDepartamento.setText(departamento);
        lblMunicipio.setText(municipio);
        lblDireccion.setText(direccion);


    }
}