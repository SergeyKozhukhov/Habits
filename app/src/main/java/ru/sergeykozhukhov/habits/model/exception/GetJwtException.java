package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке получения токена (jwt)
 */
public class GetJwtException extends Exception {
    @StringRes
    private final int messageRes;

    public GetJwtException(int messageRes) {
        this.messageRes = messageRes;
    }

    public GetJwtException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }
}
