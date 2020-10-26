package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.Alerta;
import com.example.alertaumg.Modelos.ContactosEmergencia;
import com.example.alertaumg.Modelos.RespuestaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactoEmergenciaService {

    @POST("obtener/contactos/emergencia")
    Call<RespuestaAPI<List<ContactosEmergencia>>>obtenerContactosEmergencia(
    )throws  Exception;

}
