package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке авторизации
 */
public class  AuthenticateException extends Exception {

    @StringRes
    private final int messageRes;

    public AuthenticateException(int messageRes) {
        this.messageRes = messageRes;
    }

    public AuthenticateException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
