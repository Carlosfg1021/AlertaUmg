package com.example.alertaumg.Entidades;

public class Usuarios {

    private String nombre;
    private String direccion;

    public Usuarios(){}

    public Usuarios(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }



}
