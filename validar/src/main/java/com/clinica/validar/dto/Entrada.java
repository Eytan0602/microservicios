package com.clinica.validar.dto;

public class Entrada {
    private String usuario;
    private String clave;

    public Entrada() {}

    public Entrada(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
