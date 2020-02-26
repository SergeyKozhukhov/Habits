package ru.sergeykozhukhov.habits.model.domain.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * Исключение при ошибке загрузки записей с сервера (domain слой)
 */
public class ReplicationException extends Exception {

    /**
     * Идентификтор строкового представления сообщения
     */
    @StringRes
    private final int messageRes;

    public ReplicationException(int messageRes, Throwable cause) {
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
        ReplicationException that = (ReplicationException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "ReplicationException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
