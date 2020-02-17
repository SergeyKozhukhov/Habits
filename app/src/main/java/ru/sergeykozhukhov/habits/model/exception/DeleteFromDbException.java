package ru.sergeykozhukhov.habits.model.exception;

import androidx.annotation.StringRes;

/**
 * Исключение при ошибке удаления записей из базы данных
 */
public class DeleteFromDbException extends Exception {

    @StringRes
    private final int messageRes;

    public DeleteFromDbException(String message, int messageRes) {
        super(message);
        this.messageRes = messageRes;
    }

    public DeleteFromDbException(int messageRes, Throwable cause) {
        super(cause);
        this.messageRes = messageRes;
    }

    public int getMessageRes() {
        return messageRes;
    }

}
