package com.example.alertaumg.Utilidades;

import com.example.alertaumg.Utilidades.Servicios.AlertaService;
import com.example.alertaumg.Utilidades.Servicios.UsuarioService;

public class APIUtils {
    private APIUtils(){}

    public static UsuarioService getUsuarioService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(UsuarioService.class);
    }

    public static AlertaService getAlertaService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(AlertaService.class);
    }
}
