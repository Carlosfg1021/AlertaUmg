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

    @SerializedName("fecha_commit")
    @Expose
    private Date fecha_commit;

    @SerializedName("hora_commit")
    @Expose
    private Time hora_commit;

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

    public Date getFecha_commit() {
        return fecha_commit;
    }

    public void setFecha_commit(Date fecha_commit) {
        this.fecha_commit = fecha_commit;
    }

    public Time getHora_commit() {
        return hora_commit;
    }

    public void setHora_commit(Time hora_commit) {
        this.hora_commit = hora_commit;
    }


}
