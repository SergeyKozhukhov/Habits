package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибки отправки данных о привычках на сервер
 */
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
