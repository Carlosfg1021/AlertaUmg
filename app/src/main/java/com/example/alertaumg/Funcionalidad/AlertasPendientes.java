package com.example.alertaumg.Funcionalidad;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.alertaumg.Entidades.NotificacionU;
import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Utilidades.APIUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertasPendientes {
    int res=0;

public int obtenerPendientes(int id_usuario, Context view) {


    try {
        Gson gson = new GsonBuilder().setLenient().create();
        APIUtils.getAlertaService().obtenerAlertasNoVistas(id_usuario).enqueue(new Callback<RespuestaAPI<List<Alerta>>>() {
            @Override
            public void onResponse(Call<RespuestaAPI<List<Alerta>>> call, Response<RespuestaAPI<List<Alerta>>> response) {

                if (response.isSuccessful()) {
                    RespuestaAPI<List<Alerta>> respuesta = response.body();

                    if ( respuesta.getCodigo() == 1 ){
                        List<Alerta> alerta = respuesta.getData();
                        if (alerta != null ){
                            setRes(alerta.size());
                            for(int i=0; i<alerta.size();i++){
                                Alerta alertBody = alerta.get(i);

                            }


                        }else{
                            Toast.makeText(view, "Alertas encontradas", Toast.LENGTH_SHORT).show();
                            setRes(alerta.size());
                        }
                    }else if ( respuesta.getCodigo() == 0 ){
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
    } catch (Exception e) {

    }

    return getRes();
 }


 public void setRes(int res){
    this.res=res;
 }

    public int getRes() {
        return res;
    }
}
