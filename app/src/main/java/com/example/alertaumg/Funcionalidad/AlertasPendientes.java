package com.example.alertaumg.Funcionalidad;

import android.view.View;
import android.widget.Toast;

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

    private int resultado;

    public int obtenerNumeroAlertasPendientes(int id_usuario){
        resultado=0;
        try{
            Gson gson = new GsonBuilder().setLenient().create();
            APIUtils.getAlertaService().obtenerAlertasNoVistas(id_usuario).enqueue(new Callback<RespuestaAPI<List<Alerta>>>() {
                @Override
                public void onResponse(Call<RespuestaAPI<List<Alerta>>> call, Response<RespuestaAPI<List<Alerta>>> response) {
                    if (response.isSuccessful()) {
                        RespuestaAPI<List<Alerta>> respuesta = response.body();

                        if ( respuesta.getCodigo() == 1 ){
                            List<Alerta> alerta = respuesta.getData();
                            if (alerta != null ){

                                int resp=0;
                                for(int i=0; i<alerta.size();i++){
                                    resp ++;
                                }

                                setResultado(resp);//Enviamos numero de resultados

                            }else{
                                //Toast.makeText(view.getContext(), "Alertas encontradas", Toast.LENGTH_SHORT).show();
                            }
                        }else if ( respuesta.getCodigo() == 0 ){
                           // Toast.makeText(view.getContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //Toast.makeText(view.getContext(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RespuestaAPI<List<Alerta>>> call, Throwable t) {

                }
            });

        }catch (Exception e){
            //Toast.makeText(view.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return getResultado();
    }


    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}
