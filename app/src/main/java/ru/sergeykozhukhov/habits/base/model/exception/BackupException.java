package ru.sergeykozhukhov.habits.base.model.exception;

import androidx.annotation.StringRes;

public class BackupException extends Exception {

    @StringRes
    private final int messageRes;

    public BackupException(int messageRes) {
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }
}
