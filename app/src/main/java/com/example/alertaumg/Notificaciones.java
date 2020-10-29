package com.example.alertaumg;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertaumg.Adaptadores.AdaptadorNotificacion;
import com.example.alertaumg.Adaptadores.AdaptadorUsuarios;
import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.Entidades.Usuarios;
import com.example.alertaumg.Inicio.Login;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.TipoAlerta;
import com.example.alertaumg.Modelos.Usuario;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Notificaciones extends Fragment {



    AdaptadorNotificacion adaptadorNotificacion;
    RecyclerView recyclerViewNotificacion;
    ArrayList<NotificacionU> listanotificacion;
    public int mi_id_publico;
    TipoAlerta alertaCardView;
    Usuario usuarioCardView;
    Indice indiceUsuario;
    int verificador=1;
    RelativeLayout layoutNotificacion;
    MenuItem iconoCampanita;
    public BottomNavigationView menuNavegacion;
    private int estadoNotificacion=0;

    private ArrayList<String>listaContadora = new ArrayList<>();
    public Notificaciones(int id_u){
        this.mi_id_publico = id_u;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view  = inflater.inflate(R.layout.fragment_notificaciones,container,false);
        View view2 = inflater.inflate(R.layout.activity_contenedor,container,false);

        layoutNotificacion = view.findViewById(R.id.layoutNotificacion);
        menuNavegacion = (BottomNavigationView) view2.findViewById(R.id.menu_navegacion);
        iconoCampanita = menuNavegacion.getMenu().getItem(3);

        recyclerViewNotificacion = view.findViewById(R.id.recycleview_noti);
        listanotificacion = new ArrayList<>();
        //Toast.makeText(getContext(),getActivity().getIntent().getExtras().getInt("id_usuario"),Toast.LENGTH_SHORT).show();
        //cargarlista();
       // mostraData();
      //Toast.makeText(view.getContext(),getActivity().getIntent().getExtras().getInt("id_usuario"),Toast.LENGTH_SHORT).show();

        consultaAlertas(view.getContext());
        return view;

    }
/*
    public void cargarlista(){

        listanotificacion.add(new NotificacionU("Carlos Franco","Violencia de Genero"));
    }
*/

   public int consultaAlertas(Context view){

       try{
           Gson gson = new GsonBuilder().setLenient().create();
           APIUtils.getAlertaService().obtenerAlertasNoVistas(mi_id_publico).enqueue(new Callback<RespuestaAPI<List<Alerta>>>() {
               @Override
               public void onResponse(Call<RespuestaAPI<List<Alerta>>> call, Response<RespuestaAPI<List<Alerta>>> response) {
                   if (response.isSuccessful()) {
                       RespuestaAPI<List<Alerta>> respuesta = response.body();

                       if ( respuesta.getCodigo() == 1 ){
                           verificador=1;
                           List<Alerta> alerta = respuesta.getData();
                           if (alerta != null ){

                               listanotificacion.clear();//Limpiamos antes de volver a listar


                               for(int i=0; i<alerta.size();i++){
                                   Alerta alertBody = alerta.get(i);

                                   listanotificacion.add(new NotificacionU(alerta.get(i).getNombre_usuario(),alerta.get(i).getNombre_tipo_alerta(),alertBody));

                               }
                               Toast.makeText(view,"Alertas encontradas",Toast.LENGTH_SHORT).show();
                               layoutNotificacion.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rojo));
                               iconoCampanita.setIcon(R.drawable.ic_notificacion);
                               estadoNotificacion=1;
                               mostraData();
                           }else{
                               Toast.makeText(view, "Alertas encontradas", Toast.LENGTH_SHORT).show();
                           }
                       }else if ( respuesta.getCodigo() == 0 ){
                           verificador=0;
                           layoutNotificacion.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.fondo_blanco));
                           iconoCampanita.setIcon(R.drawable.ic_notificacion_pendiente);
                           estadoNotificacion=0;
                           Toast.makeText(view, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(view, "Error en el servidor.", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onFailure(Call<RespuestaAPI<List<Alerta>>> call, Throwable t) {

               }
           });

       }catch (Exception e){
           Toast.makeText(view,e.getMessage(),Toast.LENGTH_SHORT).show();
       }

       return verificador;
   }

    public void mostraData(){

        recyclerViewNotificacion.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorNotificacion = new AdaptadorNotificacion(getContext(),listanotificacion);

        //-------------------eventos-----------------

        adaptadorNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Seleccionado: "+listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getNombre(),Toast.LENGTH_SHORT).show();

                //-----------AQUÍ MANDAMOS A LLAMAR AL ACTIVITY QUE NOS MOSTRARÁ LOS DETALLES DE LA TIENDA
                Intent intent = new Intent(getActivity().getApplicationContext(),DetalleAlerta.class);
                intent.putExtra("id_usuario",getActivity().getIntent().getExtras().getInt("id_usuario"));
                intent.putExtra("id_alerta",listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getAlertaClass().getId());
                intent.putExtra("tipo_alerta",listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getAlertaClass().getNombre_tipo_alerta());
                intent.putExtra("emisor",listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getAlertaClass().getNombre_usuario());
                intent.putExtra("descripcion",listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getAlertaClass().getDescripcion());
                intent.putExtra("latitud",listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getAlertaClass().getLatitud());
                intent.putExtra("longitud",listanotificacion.get(recyclerViewNotificacion.getChildAdapterPosition(view)).getAlertaClass().getLongitud());

                startActivity(intent);

            }
        });


        recyclerViewNotificacion.setAdapter(adaptadorNotificacion);


    }


}