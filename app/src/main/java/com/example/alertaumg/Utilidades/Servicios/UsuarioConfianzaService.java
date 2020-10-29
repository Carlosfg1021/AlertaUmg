package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.UsuarioConfianza;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioConfianzaService {

    @POST("registrar/usuario/confianza")
    @FormUrlEncoded
    Call<RespuestaAPI<String>> registrarUsuarioConfianza(
            @Field("id_usuario") int id_usuario,
            @Field("id_usuario_confianza") int id_usuario_confianza
    ) throws Exception;

    @POST("consultar/usuario/confianza")
    @FormUrlEncoded
    Call<RespuestaAPI<List<UsuarioConfianza>>> obtenerUsuariosConfianza(
            @Field("id_usuario") int id_usuario
    )throws Exception;

}
