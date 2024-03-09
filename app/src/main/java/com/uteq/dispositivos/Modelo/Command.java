package com.uteq.dispositivos.Modelo;

import com.google.gson.annotations.SerializedName;

public class Command {
    @SerializedName("code")
    private String code;

    @SerializedName("value")
    private boolean value; // Este valor debería ser booleano según la estructura que proporcionaste

    public Command(String code, boolean value) {
        this.code = code;
        this.value = value;
    }
}
