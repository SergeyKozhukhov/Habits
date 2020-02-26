package ru.sergeykozhukhov.habits.model.domain;

import java.util.Objects;

/**
 * Класс, содержащий данные пользователя для автооризации (domain слой)
 */
public class Confidentiality {

    /**
     * Почта
     */
    private String email;

    /**
     * Пароль
     */
    private String password;

    public Confidentiality(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Confidentiality that = (Confidentiality) o;
        return email.equals(that.email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "Confidentiality{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
