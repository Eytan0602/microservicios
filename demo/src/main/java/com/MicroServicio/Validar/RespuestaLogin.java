package com.MicroServicio.Validar;

public class RespuestaLogin {
    private final Medico medico;
    private final String token;

    public RespuestaLogin(Medico medico, String token) {
        this.medico = medico;
        this.token = token;
    }

    public Medico getMedico() { return medico; }
    public String getToken() { return token; }
}
