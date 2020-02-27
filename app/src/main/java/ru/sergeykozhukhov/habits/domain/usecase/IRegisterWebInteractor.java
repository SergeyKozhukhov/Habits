package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;

/**
 * Интерфейс интерактора регистрации нового пользователя
 */
public interface IRegisterWebInteractor {

    /**
     * Регистрация нового пользователя
     *
     * @param firstname            имя
     * @param lastname             фамилия
     * @param email                почта
     * @param password             пароль
     * @param passwordConfirmation повторный пароль для подтверждения
     */
    @NonNull
    Completable registerClient(@Nullable String firstname,
                               @Nullable String lastname,
                               @Nullable String email,
                               @Nullable String password,
                               @Nullable String passwordConfirmation);

}
