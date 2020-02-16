package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке добавления записей на сервер
 */
public class InsertWebException extends Exception {

    @StringRes
    private final int messageRes;

    public InsertWebException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public InsertWebException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
