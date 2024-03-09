package com.uteq.dispositivos.Modelo;

public class AulaDetalle {
    private final int id_auladetalle;
    private final int aula;
    private final int usuario;
    private final int rol;

    public AulaDetalle(int id_auladetalle, int id_aula, int id_usuario, int id_rol) {
        this.id_auladetalle = id_auladetalle;
        this.aula = id_aula;
        this.usuario = id_usuario;
        this.rol = id_rol;
    }

    public int getId_auladetalle() {
        return id_auladetalle;
    }

    public int getId_aula() {
        return aula;
    }

    public int getId_usuario() {
        return usuario;
    }

    public int getId_rol() {
        return rol;
    }
}

