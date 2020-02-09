package ru.sergeykozhukhov.habits.notes.backup;

public class Authentication {

    private String message;
    private String jwt;

    public Authentication(String message, String jwt) {
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
