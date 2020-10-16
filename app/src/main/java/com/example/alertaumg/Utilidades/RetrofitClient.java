package com.example.alertaumg.Utilidades;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    /**
     * Atributo del ip por defecto que leera el cliente de retrofit
     */
    // para desarrollo
    //public static String ip_default = "192.168.0.8:8000";
    // para produccion
    public static String ip_default = "sistemaalerta.000webhostapp.com";

    /**
     * Atributo para conocer la direccion donde se encuentra alojada los servicios api rest
     */
    public static String API_URL = "http://" + ip_default +"/api/";

    /**
     * Atributo static para definiri el time out por defecto del HttpClient al conectar al servidor.
     */
    private static int TIMEOUT_CONNECT = 60;

    /**
     * Atributo static para definir el time out por defcto del HttpClient al leer datos del servidor.
     */
    private static int TIMEOUT_READ = 60;

    /**
     * Atributo static para definir el time out por defecto del HttpClient al escribir datos en el servidor.
     */
    private static int TIMEOUT_WRITE = 60;

    /**
     * Atributo para establece el tiempo de espera para la conexion, y otras transacciones
     */
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .build();

    /**
     * Atributo para el builder de retrofit, permitiendo crear servicios (este es por default)
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create());

    /**
     * Atributo para crear el servicio retrofit por default
     */
    private static Retrofit retrofit = null;

    /**
     * Metodo estatico para obtener el cliente de retrofit
     * @param url
     * @return
     */
    public static Retrofit getClient(String url){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

    /**
     * Metodo estatico para cambiar el valor de la {@link #API_URL}
     * @param ip_default
     */
    public static void setApiUrl(String ip_default) {
        API_URL = "http://" + ip_default +"/api/";
    }

    /**
     * Metodo estatico para cambiar la ip y cambiar la base url de retrofit
     * @param nuevaIp
     */
    public static void cambiarBaseURL(String nuevaIp){
        // cambiamos al ip por defecto a la nueva ip introducida
        RetrofitClient.ip_default = nuevaIp;
        // cambias la API_URL con la nueva ip introducida
        RetrofitClient.setApiUrl(nuevaIp);

        // volvemos a recrear el objeto de retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
