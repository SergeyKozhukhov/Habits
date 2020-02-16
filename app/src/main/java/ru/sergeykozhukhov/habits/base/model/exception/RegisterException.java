package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибки регистрации пользователя
 */
public class RegisterException extends Exception{

    @StringRes
    private final int messageRes;

    public RegisterException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public RegisterException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
