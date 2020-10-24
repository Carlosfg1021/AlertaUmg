package com.example.alertaumg.Utilidades;

import com.example.alertaumg.Utilidades.Servicios.AlertaService;
import com.example.alertaumg.Utilidades.Servicios.ContactoEmergenciaService;
import com.example.alertaumg.Utilidades.Servicios.DepartamentoService;
import com.example.alertaumg.Utilidades.Servicios.MunicipioService;
import com.example.alertaumg.Utilidades.Servicios.TipoAlertaService;
import com.example.alertaumg.Utilidades.Servicios.UsuarioConfianzaService;
import com.example.alertaumg.Utilidades.Servicios.UsuarioService;

import retrofit2.Retrofit;

public class APIUtils {
    private APIUtils(){}

    public static UsuarioService getUsuarioService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(UsuarioService.class);
    }

    public static AlertaService getAlertaService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(AlertaService.class);
    }

    public static DepartamentoService getDepartamentoService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(DepartamentoService.class);
    }

    public static MunicipioService getMunicipioService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(MunicipioService.class);
    }

    public static UsuarioConfianzaService getUsuarioConfianzaService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(UsuarioConfianzaService.class);
    }

    public static TipoAlertaService getTipoAlertaService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(TipoAlertaService.class);
    }

    public static ContactoEmergenciaService getContactoEmergenciaService(){
        return RetrofitClient.getClient(RetrofitClient.API_URL).create(ContactoEmergenciaService.class);
    }
}
