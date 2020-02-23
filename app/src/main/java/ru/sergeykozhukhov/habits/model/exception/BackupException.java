package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибке добавления записей на сервер
 */
public class BackupException extends Exception {

    @StringRes
    private final int messageRes;

    public BackupException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public BackupException(int messageRes, Throwable cause) {
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
        BackupException that = (BackupException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "BackupException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
