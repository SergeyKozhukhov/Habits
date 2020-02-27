package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Интерфейс интерактора получения нового экземпляра класса Registration
 */
public interface IBuildRegistrationInteractor {

    /**
     * Получение новго экземпляра класса Registration
     *
     * @param firstaname           имя
     * @param lastname             фамилия
     * @param email                почта
     * @param password             пароль
     * @param passwordConfirmation повторный пароль для подтверждения
     * @return экземпляр класса Registration
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    Registration build(@Nullable String firstaname,
                       @Nullable String lastname,
                       @Nullable String email,
                       @Nullable String password,
                       @Nullable String passwordConfirmation) throws BuildException;
}
