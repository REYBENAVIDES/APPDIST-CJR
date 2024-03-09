package com.uteq.dispositivos.Modelo;

public class Usuario {
    private final int id_usuario;
    private final String usuario;
    private final String email;
    private final String contraseña;

    public Usuario(int idUsuario, String usuario, String email, String contraseña) {
        this.id_usuario = idUsuario;
        this.usuario = usuario;
        this.email = email;
        this.contraseña = contraseña;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + id_usuario +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }
}
