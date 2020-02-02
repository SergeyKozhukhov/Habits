package ru.sergeykozhukhov.habits.notes.retrofit.data.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResult {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("jwt")
    @Expose
    private String jwt;

    public AuthResult(String message, String jwt) {
        this.message = message;
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
