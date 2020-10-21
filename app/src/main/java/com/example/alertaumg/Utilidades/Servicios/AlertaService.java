package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AlertaService {

    @POST("registrar/alerta")
    @FormUrlEncoded
    Call<RespuestaAPI<String>> registrarAlerta(
            @Field("id_tipo_alerta") int id_tipo_alerta,
            @Field("descripcion") String descripcion,
            @Field("longitud") String longitud,
            @Field("latitud") String latitud,
            @Field("id_usuario") int id_usuario

    ) throws Exception;

}
