package com.example.dispositivosinteligentes.Modelo;

public class FacultadCompartida {
    private final int id_facultadCompartida;
    private final int facultad;
    private final int usuario;
    private final boolean estado;

    public FacultadCompartida(int idFacultadCompartida, int idFacultad, int idUsuario, boolean estado) {
        this.id_facultadCompartida = idFacultadCompartida;
        this.facultad = idFacultad;
        this.usuario = idUsuario;
        this.estado = estado;
    }

    public int getIdFacultadCompartida() {
        return id_facultadCompartida;
    }

    public int getIdFacultad() {
        return facultad;
    }

    public int getIdUsuario() {
        return usuario;
    }

    public boolean isEstado() {
        return estado;
    }
}
