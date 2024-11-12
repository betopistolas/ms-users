package com.neoris.mspracticauser.model;

import java.util.Set;

public class LoginResponse {
    private String correo;
    private String token;

    public LoginResponse(String correo, String token) {
        this.correo = correo;
        this.token = token;
    }

    public String getCorreo() {
        return correo;
    }

    public String getToken() {
        return token;
    }

}