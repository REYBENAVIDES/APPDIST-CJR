package com.example.dispositivosinteligentes.Modelo;

public class Facultad {
    private final int id_facultad;
    private final String nombre;
    private final boolean Estado;
    private final int id_usuario;
    private final int cantidad_aula;
    private final int cantidad_dispositivos;
    private final int cantidad_dispositivos_activo;

    public Facultad(int id_facultad, String nombre, boolean Estado, int id_usuario, int cantidad_aula, int cantidad_dispositivos, int cantidad_dispositivos_activo) {
        this.id_facultad = id_facultad;
        this.nombre = nombre;
        this.Estado = Estado;
        this.id_usuario = id_usuario;
        this.cantidad_aula = cantidad_aula;
        this.cantidad_dispositivos = cantidad_dispositivos;
        this.cantidad_dispositivos_activo = cantidad_dispositivos_activo;
    }

    // Getters
    public int getId_facultad() {
        return id_facultad;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstado() {
        return Estado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getCantidad_aula() {
        return cantidad_aula;
    }

    public int getCantidad_dispositivos() {
        return cantidad_dispositivos;
    }

    public int getCantidad_dispositivos_activo() {
        return cantidad_dispositivos_activo;
    }
}
