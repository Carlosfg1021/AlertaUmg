package com.example.alertaumg.Modelos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Telefonos {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_contacto_emergencia")
    @Expose
    private int id_contacto_emergencia;

    @SerializedName("numero_telefono")
    @Expose
    private String numero_telefono;

    @SerializedName("estado")
    @Expose
    private int estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_contacto_emergencia() {
        return id_contacto_emergencia;
    }

    public void setId_contacto_emergencia(int id_contacto_emergencia) {
        this.id_contacto_emergencia = id_contacto_emergencia;
    }

    public String getNumero_telefono() {
        return numero_telefono;
    }

    public void setNumero_telefono(String numero_telefono) {
        this.numero_telefono = numero_telefono;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
