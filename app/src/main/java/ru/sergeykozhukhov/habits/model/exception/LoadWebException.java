package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке загрузки записей с сервера
 */
public class LoadWebException extends Exception {

    @StringRes
    private final int messageRes;

    public LoadWebException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public LoadWebException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
