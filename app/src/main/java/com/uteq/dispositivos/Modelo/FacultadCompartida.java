package com.uteq.dispositivos.Modelo;

public class FacultadCompartida {
    private final int id_facultadCompartida;
    private final Facultad facultad;
    private final Usuario usuario;
    private final boolean estado;

    public FacultadCompartida(int idFacultadCompartida, Facultad idFacultad, Usuario idUsuario, boolean estado) {
        this.id_facultadCompartida = idFacultadCompartida;
        this.facultad = idFacultad;
        this.usuario = idUsuario;
        this.estado = estado;
    }

    public int getIdFacultadCompartida() {
        return id_facultadCompartida;
    }

    public Facultad getIdFacultad() {
        return facultad;
    }

    public Usuario getIdUsuario() {
        return usuario;
    }

    public boolean isEstado() {
        return estado;
    }
}
