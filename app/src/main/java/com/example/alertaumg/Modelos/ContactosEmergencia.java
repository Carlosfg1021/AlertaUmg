package com.example.alertaumg.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactosEmergencia {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_usuario")
    @Expose
    private int id_usuario;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("estado")
    @Expose
    private int estado;

    @SerializedName("numeros_telefono")
    @Expose
    private String numeros_telefono;

    @SerializedName("telefonos")
    @Expose
    private List<String> telefonos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNumeros_telefono() {
        return numeros_telefono;
    }

    public void setNumeros_telefono(String numeros_telefono) {
        this.numeros_telefono = numeros_telefono;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }
}
