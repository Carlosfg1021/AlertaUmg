package com.example.alertaumg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.alertaumg.Funcionalidad.EmitirAlerta;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TipoAlerta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipoAlerta extends Fragment {

    ImageButton btnAlertaVandalismo, btnAlertaRobo, btnAlertaAgresion, btnAlertaViolenciaGenero,
    btnAlertaAcosoEscolar, btnAlertaAgresionSexual,btnAlertaIncendio, btnAlertaInundacion,
    btnAlertaDerrumbe;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TipoAlerta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TipoAlerta.
     */
    // TODO: Rename and change types and number of parameters
    public static TipoAlerta newInstance(String param1, String param2) {
        TipoAlerta fragment = new TipoAlerta();
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
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_tipo_alerta,container,false);

        btnAlertaVandalismo = view.findViewById(R.id.btnAlertaVandalismo);
        btnAlertaRobo = view.findViewById(R.id.btnAlertaRobo);
        btnAlertaAgresion = view.findViewById(R.id.btnAlertaAgresion);

        btnAlertaViolenciaGenero = view.findViewById(R.id.btnAlertaViolenciaGenero);
        btnAlertaAcosoEscolar = view.findViewById(R.id.btnAlertaAcosoEscolar);
        btnAlertaAgresionSexual = view.findViewById(R.id.btnAlertaAgresionSexual);

        btnAlertaIncendio = view.findViewById(R.id.btnAlertaIncendio);
        btnAlertaInundacion = view.findViewById(R.id.btnAlertaInundacion);
        btnAlertaDerrumbe = view.findViewById(R.id.btnAlertaDerrumbe);


        btnAlertaVandalismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",4);
                intent.putExtra("tipoAlerta","Vandalismo");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaRobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",3);
                intent.putExtra("tipoAlerta","Robo");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaAgresion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",5);
                intent.putExtra("tipoAlerta","Agresión");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaViolenciaGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",6);
                intent.putExtra("tipoAlerta","Violencia de género");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaAcosoEscolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",7);
                intent.putExtra("tipoAlerta","Acoso escolar");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaAgresionSexual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",8);
                intent.putExtra("tipoAlerta","Agresión sexual");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaIncendio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",9);
                intent.putExtra("tipoAlerta","Incendio");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaInundacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",10);
                intent.putExtra("tipoAlerta","Inundación");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        btnAlertaDerrumbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EmitirAlerta.class);
                intent.putExtra("alerta",11);
                intent.putExtra("tipoAlerta","Derrumbe");
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                startActivity(intent);
            }
        });

        return view;
    }


}