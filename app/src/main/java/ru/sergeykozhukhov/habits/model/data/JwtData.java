package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Класс, содержащий token (jwt) пользователя (data слой)
 */
public class JwtData {

    /**
     * Token (jwt)
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtData jwtData = (JwtData) o;
        return jwt.equals(jwtData.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt);
    }

    @Override
    public String toString() {
        return "JwtData{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
