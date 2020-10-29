package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {

    TextView lblNombre, lblApellido, lblDepartamento, lblMunicipio, lblDireccion, lblpersonaNombre, lblTelefono;
    ImageView personaImagenPerfil;
    Button btnEditar;
    public static String urlFoto;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment

        btnEditar = vista.findViewById(R.id.btnEditarPerfil);

        lblpersonaNombre = vista.findViewById(R.id.personaNombre);
        lblNombre = vista.findViewById(R.id.lblNombre);
        lblApellido = vista.findViewById(R.id.lblApellido);
        lblDepartamento = vista.findViewById(R.id.lblDepartamento);
        lblMunicipio = vista.findViewById(R.id.lblMunicipio);
        lblDireccion = vista.findViewById(R.id.lblDireccion);
        lblTelefono = vista.findViewById(R.id.lblTelefono);

        personaImagenPerfil = vista.findViewById(R.id.EditarImgPerfil);
//lblApellido;
        lblNombre.setText(getActivity().getIntent().getExtras().getString("nombre"));
        lblApellido.setText(getActivity().getIntent().getExtras().getString("apellido"));
        lblTelefono.setText(getActivity().getIntent().getExtras().getString("telefono"));

        lblpersonaNombre.setText(lblNombre.getText()+" "+lblApellido.getText());
        urlFoto = getActivity().getIntent().getExtras().getString(("fotografia"));

       // Toast.makeText(getActivity().getApplicationContext(),urlFoto,Toast.LENGTH_SHORT).show();

        //cargar photo
        try{
            Glide.with(getActivity())
                    .load(urlFoto )
                    .into((ImageView) personaImagenPerfil);
        }catch (Exception e){

        }




        String dir = getActivity().getIntent().getExtras().getString("direccion");

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

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"Presionado",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getApplicationContext(),EditUser.class);


                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                intent.putExtra("nombre", lblNombre.getText().toString());
                intent.putExtra("apellido",lblApellido.getText().toString());
                intent.putExtra("telefono",lblTelefono.getText().toString());
                intent.putExtra("direccion",lblDireccion.getText().toString());
                intent.putExtra("correo",getActivity().getIntent().getExtras().getString("correo"));
                intent.putExtra("departamento", lblDepartamento.getText().toString());
                intent.putExtra("municipio", lblMunicipio.getText());


                startActivity(intent);
            }
        });

        return vista;
    }
}