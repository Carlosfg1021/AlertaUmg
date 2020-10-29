package com.example.alertaumg.Entidades;

import com.example.alertaumg.Modelos.Usuario;

public class Usuarios {


    private String nombre;
    private String direccion;
    private Usuario usuario;
    private Usuarios datosUsuarioSeleccionado;

    public Usuarios(){}

    public Usuarios(String nombre, String direccion,Usuario usser) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.usuario = usser;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

   public Usuario getUsuario(){return usuario;}


}
