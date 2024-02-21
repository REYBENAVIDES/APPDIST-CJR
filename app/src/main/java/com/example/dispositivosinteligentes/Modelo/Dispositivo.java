package com.example.dispositivosinteligentes.Modelo;

public class Dispositivo {

    private final int idDispositivo;
    private final int idAula;
    private final String nombre;
    private final String marca;
    private final String modelo;
    private final boolean estado;
    private final boolean estado2 = false;

    public Dispositivo(int idDispositivo, int idAula, String nombre, String marca, String modelo, boolean estado) {
        this.idDispositivo = idDispositivo;
        this.idAula = idAula;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public int getIdAula() {
        return idAula;
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

    public boolean isEstado2() {
        return estado2;
    }
}
