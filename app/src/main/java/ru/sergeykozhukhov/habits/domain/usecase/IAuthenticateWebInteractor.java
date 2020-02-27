package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;

/**
 * Интерфейс интерктора входа пользователя в свой аккаунт
 */
public interface IAuthenticateWebInteractor {

    /**
     * Аутентификация пользователя
     *
     * @param email    почта
     * @param password пароль
     */
    @NonNull
    Completable authenticateClient(@Nullable String email,
                                   @Nullable String password);

}
