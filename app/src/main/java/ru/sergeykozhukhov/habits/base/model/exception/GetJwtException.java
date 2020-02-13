package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

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
