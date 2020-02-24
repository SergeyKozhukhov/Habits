package ru.sergeykozhukhov.habits.model.domain.exception;

import androidx.annotation.StringRes;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildException that = (BuildException) o;
        return messageRes == that.messageRes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRes);
    }

    @Override
    public String toString() {
        return "BuildException{" +
                "messageRes=" + messageRes +
                '}';
    }
}
