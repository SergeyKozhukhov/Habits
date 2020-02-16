package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при создании экземляра.
 * В данном случае применяется к созданию habit, confidentiality, registration
 */
public class BuildException extends Exception {
    @StringRes
    private final int messageRes;

    public BuildException(int messageRes) {
        this.messageRes = messageRes;
    }

    public BuildException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }
}
