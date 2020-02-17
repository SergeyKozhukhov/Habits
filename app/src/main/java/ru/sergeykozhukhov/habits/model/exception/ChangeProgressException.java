package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

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

}
