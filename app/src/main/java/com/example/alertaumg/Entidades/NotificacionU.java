package com.example.alertaumg.Entidades;

public class NotificacionU {

    private String nombre;
    private String alerta;

    public NotificacionU(){}

    public NotificacionU(String nombre, String alerta) {
        this.nombre = nombre;
        this.alerta = alerta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlerta() {
        return alerta;
    }


}
