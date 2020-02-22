package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибке удаления записей из базы данных
 */
public class DeleteFromDbException extends Exception {

    @StringRes
    private final int messageRes;

    public DeleteFromDbException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public DeleteFromDbException(int messageRes, Throwable cause) {
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
        DeleteFromDbException that = (DeleteFromDbException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "DeleteFromDbException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
