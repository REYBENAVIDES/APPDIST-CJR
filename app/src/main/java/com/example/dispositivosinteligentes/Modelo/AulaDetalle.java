package com.example.dispositivosinteligentes.Modelo;

public class AulaDetalle {
    private final int id_auladetalle;
    private final int id_aula;
    private final int id_usuario;
    private final int id_rol;

    public AulaDetalle(int id_auladetalle, int id_aula, int id_usuario, int id_rol) {
        this.id_auladetalle = id_auladetalle;
        this.id_aula = id_aula;
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
    }

    public int getId_auladetalle() {
        return id_auladetalle;
    }

    public int getId_aula() {
        return id_aula;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getId_rol() {
        return id_rol;
    }
}

