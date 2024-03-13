package com.uteq.dispositivos.Modelo;

public class Dispositivo {

    private final int id_dispositivo;
    private final Aula aula;
    private final String nombre;
    private final String devId;
    private final String marca;
    private final String modelo;
    private boolean estado;
    private boolean estado2 = false;

    public Dispositivo(int idDispositivo, Aula idAula, String nombre, String devId, String marca, String modelo, boolean estado) {
        this.id_dispositivo = idDispositivo;
        this.aula = idAula;
        this.nombre = nombre;
        this.devId = devId;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
    }

    public String getDevId() { return devId; }

    public int getIdDispositivo() {
        return id_dispositivo;
    }

    public Aula getIdAula() {
        return aula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstado2() {
        return estado2;
    }

    public void setEstado2(boolean estado) {
        this.estado2 = estado;
    }
}
