package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.Alerta;
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

    @POST("consultar/alertas/usuario")
    @FormUrlEncoded
    Call<RespuestaAPI<List<Alerta>>>obtenerAlertasUsuario(
            @Field("id_usuario") int id
    )throws Exception;

    @POST("obtener/alerta/no/vistas")
    @FormUrlEncoded

    Call<RespuestaAPI<List<Alerta>>>obtenerAlertasNoVistas(
            @Field("id_usuario") int id
    )throws  Exception;

    @POST("marcar/vista/alerta")
    @FormUrlEncoded
    Call<RespuestaAPI<Alerta>>marcarAlertaVista(
            @Field("id_alerta") int id_alerta,
            @Field("id_usuario") int id_usuario
    )throws Exception;

}
