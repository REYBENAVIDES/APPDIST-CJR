package com.example.dispositivosinteligentes.Modelo;

public class HistorialDispositivo {
    private final int id_historial;
    private final int id_dispositivo;
    private final int id_usuario;
    private final String accion;
    private final String fecha;

    public HistorialDispositivo(int idHistorial, int idDispositivo, int idUsuario, String accion, String fecha) {
        this.id_historial = idHistorial;
        this.id_dispositivo = idDispositivo;
        this.id_usuario = idUsuario;
        this.accion = accion;
        this.fecha = fecha;
    }

    public int getIdHistorial() {
        return id_historial;
    }

    public int getIdDispositivo() {
        return id_dispositivo;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public String getAccion() {
        return accion;
    }

    public String getFecha() {
        return fecha;
    }
}
