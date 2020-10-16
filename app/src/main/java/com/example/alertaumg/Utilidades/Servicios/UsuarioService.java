package com.example.alertaumg.Utilidades.Servicios;

import com.example.alertaumg.Modelos.RespuestaAPI;
import com.example.alertaumg.Modelos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioService {
    /**
     * Metodo para realizar el login, en la ruta "/login".
     * @param email    email del usuario para loguear
     * @param password contraseña que establecio el usuario
     * @return regresa un objeto Usuario si es valido; de lo contrario objeto con sus campos null
     * @throws Exception en caso de error devolvera un objeto con id 0, y en username el mensaje de error
     */
    @POST("login")
    @FormUrlEncoded
    Call<RespuestaAPI<Usuario>> login(
            @Field("email") String email,
            @Field("password") String password
    ) throws Exception;

    @POST("registrar/usuario")
    @FormUrlEncoded
    Call<RespuestaAPI<String>> registrarUsuario(
            @Field("nombre") String nombre,
            @Field("apellido") String apellido,
            @Field("numero_telefono") String numero_telefono,
            @Field("email") String email,
            @Field("contrasenia") String contrasenia,
            @Field("contrasenia_confirm") String contrasenia_confirm,
            @Field("direccion") String direccion
    ) throws Exception;
}
