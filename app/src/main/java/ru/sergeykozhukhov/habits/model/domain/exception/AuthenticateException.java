package ru.sergeykozhukhov.habits.model.domain.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибке авторизации (domain слой)
 */
public class AuthenticateException extends Exception {

    /**
     * Идентификтор строкового представления сообщения
     */
    @StringRes
    private final int messageRes;

    public AuthenticateException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticateException that = (AuthenticateException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "AuthenticateException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
