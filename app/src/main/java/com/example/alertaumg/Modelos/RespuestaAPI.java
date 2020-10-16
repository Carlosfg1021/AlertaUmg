package com.example.alertaumg.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class RespuestaAPI<E> {
    @SerializedName("codigo")
    @Expose
    private int codigo; // estado codigo, 1=exito, 2=falla

    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    @SerializedName("errors")
    @Expose
    private ArrayList<String> errors;

    @SerializedName("data")
    @Expose
    private E data;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
