package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибке получения токена (jwt)
 */
public class GetJwtException extends Exception {
    @StringRes
    private final int messageRes;

    public GetJwtException(int messageRes) {
        this.messageRes = messageRes;
    }

    public GetJwtException(int messageRes, Throwable cause) {
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
        GetJwtException that = (GetJwtException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "GetJwtException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
