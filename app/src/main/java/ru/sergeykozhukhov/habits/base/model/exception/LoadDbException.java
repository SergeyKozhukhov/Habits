package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке загрзуки записей из базы данных
 */
public class LoadDbException extends Exception{

    @StringRes
    private final int messageRes;

    public LoadDbException(int messageRes) {
        this.messageRes = messageRes;
    }

    public LoadDbException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
