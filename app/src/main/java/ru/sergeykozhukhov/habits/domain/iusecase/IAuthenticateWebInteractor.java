package ru.sergeykozhukhov.habits.domain.iusecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;

/**
 * Интерфейс интерктора входа пользователя в свой аккаунт
 */
public interface IAuthenticateWebInteractor {

    @NonNull
    Completable authenticateClient(@Nullable String email,
                                   @Nullable String password);

}
