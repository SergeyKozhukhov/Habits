package ru.sergeykozhukhov.habits.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Класс, содержащий данные пользователя для автооризации (data слой)
 */
public class ConfidentialityData {

    /**
     * Почта
     */
    @SerializedName("email")
    @Expose
    private String email;

    /**
     * Пароль
     */
    @SerializedName("password")
    @Expose
    private String password;

    public ConfidentialityData(String email, String password) {
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
        ConfidentialityData that = (ConfidentialityData) o;
        return email.equals(that.email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "ConfidentialityData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
