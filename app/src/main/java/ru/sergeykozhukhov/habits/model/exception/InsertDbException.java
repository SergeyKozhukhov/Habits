package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке добавления записей в базу данных
 */
public class InsertDbException extends Exception {

    @StringRes
    private final int messageRes;

    public InsertDbException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public InsertDbException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}