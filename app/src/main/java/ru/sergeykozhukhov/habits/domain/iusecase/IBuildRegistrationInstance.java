package ru.sergeykozhukhov.habits.domain.iusecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Интерфейс получения нового экземпляра класса registration
 */
public interface IBuildRegistrationInstance {
    @NonNull
    Registration build(@Nullable String firstaname,
                       @Nullable String lastname,
                       @Nullable String email,
                       @Nullable String password,
                       @Nullable String passwordConfirmation) throws BuildException, BuildException;
}