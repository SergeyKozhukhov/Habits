package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;

/**
 * Интерфейс интерактора регистрирования нового пользователя
 */
public interface IRegisterWebInteractor {

    @NonNull
    Completable registerClient(@Nullable String firstname,
                               @Nullable String lastname,
                               @Nullable String email,
                               @Nullable String password,
                               @Nullable String passwordConfirmation);

}
