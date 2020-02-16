package ru.sergeykozhukhov.habits.base.model.domain;

import java.util.Objects;

/**
 * Класс, содержащий token (jwt) пользователя (domain слой)
 */
public class Jwt {

    private String jwt;

    public Jwt(String jwt) {
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
        Jwt jwt1 = (Jwt) o;
        return jwt.equals(jwt1.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt);
    }

    @Override
    public String toString() {
        return "Jwt{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
