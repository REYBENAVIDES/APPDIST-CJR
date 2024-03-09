package com.uteq.dispositivos.Modelo;

public class Rol {
    private final int idRol;
    private final String descripcion;
    private final boolean estado;

    public Rol(int idRol, String descripcion, boolean estado) {
        this.idRol = idRol;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEstado() {
        return estado;
    }
}
