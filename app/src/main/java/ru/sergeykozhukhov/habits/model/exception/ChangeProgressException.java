package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибки внесении исправленных данных по привычкам
 */
public class ChangeProgressException extends Exception {

    @StringRes
    private final int messageRes;

    public ChangeProgressException(int messageRes) {
        this.messageRes = messageRes;
    }

    public ChangeProgressException(int messageRes, Throwable cause) {
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
        ChangeProgressException that = (ChangeProgressException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "ChangeProgressException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
