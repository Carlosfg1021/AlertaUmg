package com.example.alertaumg.Entidades;

import com.example.alertaumg.Modelos.Alerta;

public class NotificacionU {

    private String nombre;
    private String alerta;
    private Alerta alertaClass;


    public NotificacionU(){}

    public NotificacionU(String nombre, String alerta, Alerta alert) {
        this.nombre = nombre;
        this.alerta = alerta;
        this.setAlertaClass(alert);
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlerta() {
        return alerta;
    }


    public Alerta getAlertaClass() {
        return alertaClass;
    }

    public void setAlertaClass(Alerta alertaClass) {
        this.alertaClass = alertaClass;
    }
}
