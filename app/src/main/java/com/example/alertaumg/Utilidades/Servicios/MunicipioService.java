package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.Departamento;
import com.example.alertaumg.Modelos.Municipio;
import com.example.alertaumg.Modelos.RespuestaAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MunicipioService {

    @POST("obtener/municipios")
    @FormUrlEncoded
    Call<RespuestaAPI<List<Municipio>>> listarMunicipios(
            @Field("id_departamento") int id_departamento
    ) throws Exception;

}
