package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.TipoAlerta;
import com.example.alertaumg.Modelos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TipoAlertaService {

    @POST("obtener/alerta")
    @FormUrlEncoded
    Call<RespuestaAPI<TipoAlerta>> obtenerAlertaPorId(
            @Field("id_alerta") int id
    ) throws Exception;

}
