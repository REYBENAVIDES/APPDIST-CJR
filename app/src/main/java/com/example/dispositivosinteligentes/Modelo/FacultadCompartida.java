package com.example.dispositivosinteligentes.Modelo;

public class FacultadCompartida {
    private final int id_facultadCompartida;
    private final int id_facultad;
    private final int id_usuario;
    private final boolean estado;

    public FacultadCompartida(int idFacultadCompartida, int idFacultad, int idUsuario, boolean estado) {
        this.id_facultadCompartida = idFacultadCompartida;
        this.id_facultad = idFacultad;
        this.id_usuario = idUsuario;
        this.estado = estado;
    }

    public int getIdFacultadCompartida() {
        return id_facultadCompartida;
    }

    public int getIdFacultad() {
        return id_facultad;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public boolean isEstado() {
        return estado;
    }
}
