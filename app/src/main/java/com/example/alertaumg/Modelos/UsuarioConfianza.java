package com.example.alertaumg.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsuarioConfianza {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_usuario")
    @Expose
    private int id_usuario;

    @SerializedName("id_usuario_confianza")
    @Expose
    private int id_usuario_confianza;

    @SerializedName("estado")
    @Expose
    private int estado;

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

    public int getId_usuario_confianza() {
        return id_usuario_confianza;
    }

    public void setId_usuario_confianza(int id_usuario_confianza) {
        this.id_usuario_confianza = id_usuario_confianza;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
