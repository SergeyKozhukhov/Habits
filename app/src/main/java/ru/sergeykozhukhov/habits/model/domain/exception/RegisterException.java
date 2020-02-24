package ru.sergeykozhukhov.habits.model.domain.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибки регистрации пользователя
 */
public class RegisterException extends Exception{

    @StringRes
    private final int messageRes;

    public RegisterException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public RegisterException(int messageRes, Throwable cause) {
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
        RegisterException that = (RegisterException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "RegisterException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
