package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {

    TextView lblNombre, lblApellido, lblDepartamento, lblMunicipio, lblDireccion, lblpersonaNombre;
    ImageView personaImagenPerfil;

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
        lblpersonaNombre = vista.findViewById(R.id.personaNombre);
        lblNombre = vista.findViewById(R.id.lblNombre);
        lblApellido = vista.findViewById(R.id.lblApellido);
        lblDepartamento = vista.findViewById(R.id.lblDepartamento);
        lblMunicipio = vista.findViewById(R.id.lblMunicipio);
        lblDireccion = vista.findViewById(R.id.lblDireccion);


        lblNombre.setText(getActivity().getIntent().getExtras().getString("nombre"));
        lblApellido.setText(getActivity().getIntent().getExtras().getString("apellido"));

        lblpersonaNombre.setText(lblNombre.getText()+" "+lblApellido.getText());

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

        return vista;
    }
}