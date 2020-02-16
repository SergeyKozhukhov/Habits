package ru.sergeykozhukhov.habits.base.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс, содержащий token (jwt) пользователя (data слой)
 */
public class JwtData {

    @SerializedName("jwt")
    @Expose
    private String jwt;

    public JwtData(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
