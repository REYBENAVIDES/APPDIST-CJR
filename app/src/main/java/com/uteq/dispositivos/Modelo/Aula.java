package com.uteq.dispositivos.Modelo;

public class Aula {
    private final int id_aula;
    private final String nombre;
    private final Facultad facultad;
    private final boolean estado;
    private final int cantidad_dispositivos;
    private final int cantidad_dispositivos_activo;

    public Aula(int id_aula, String nombre, Facultad facultad, boolean estado, int cantidad_dispositivos, int cantidad_dispositivos_activo) {
        this.id_aula = id_aula;
        this.nombre = nombre;
        this.facultad = facultad;
        this.estado = estado;
        this.cantidad_dispositivos = cantidad_dispositivos;
        this.cantidad_dispositivos_activo = cantidad_dispositivos_activo;
    }

    public int getId_aula() {
        return id_aula;
    }

    public String getNombre() {
        return nombre;
    }

    public Facultad getId_facultad() {
        return facultad;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getCantidad_dispositivos() {
        return cantidad_dispositivos;
    }

    public int getCantidad_dispositivos_activo() {
        return cantidad_dispositivos_activo;
    }
}
