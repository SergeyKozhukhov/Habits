package ru.sergeykozhukhov.habits.base.model.domain;

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
}
