package com.example.dispositivosinteligentes.Modelo;

public class HistorialDispositivo {
    private final int id_historial;
    private final int dispositivo;
    private final int usuario;
    private final String accion;
    private final String fecha;

    public HistorialDispositivo(int idHistorial, int idDispositivo, int idUsuario, String accion, String fecha) {
        this.id_historial = idHistorial;
        this.dispositivo = idDispositivo;
        this.usuario = idUsuario;
        this.accion = accion;
        this.fecha = fecha;
    }

    public int getIdHistorial() {
        return id_historial;
    }

    public int getIdDispositivo() {
        return dispositivo;
    }

    public int getIdUsuario() {
        return usuario;
    }

    public String getAccion() {
        return accion;
    }

    public String getFecha() {
        return fecha;
    }
}
