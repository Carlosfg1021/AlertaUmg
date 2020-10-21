package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.Departamento;
import com.example.alertaumg.Modelos.RespuestaAPI;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DepartamentoService {

    @POST("obtener/departamentos")
    @FormUrlEncoded
    Call<RespuestaAPI<Departamento>> listarDepartamentos(

    ) throws Exception;

}
