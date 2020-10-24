package com.example.alertaumg.Modelos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

public class Alerta {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_tipo_alerta")
    @Expose
    private int id_tipo_alerta;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("foto")
    @Expose
    private String foto;

    @SerializedName("id_usuario")
    @Expose
    private int id_usuario;

    @SerializedName("estado")
    @Expose
    private int estado;


    @SerializedName("nombre_tipo_alerta")
    @Expose
    private String nombre_tipo_alerta;

    @SerializedName("nombre_usuario")
    @Expose
    private String nombre_usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tipo_alerta() {
        return id_tipo_alerta;
    }

    public void setId_tipo_alerta(int id_tipo_alerta) {
        this.id_tipo_alerta = id_tipo_alerta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }



    public String getNombre_tipo_alerta() {
        return nombre_tipo_alerta;
    }

    public void setNombre_tipo_alerta(String nombre_tipo_alerta) {
        this.nombre_tipo_alerta = nombre_tipo_alerta;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
}
