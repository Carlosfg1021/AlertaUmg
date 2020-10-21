package com.example.alertaumg.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Time;

public class Municipio {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_departamento")
    @Expose
    private int id_departamento;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

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

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
