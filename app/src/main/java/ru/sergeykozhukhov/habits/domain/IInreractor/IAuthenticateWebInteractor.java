package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

/**
 * Интерфейс интерктора входа пользователя в свой аккаунт
 */
public interface IAuthenticateWebInteractor {

    @NonNull
    Completable authenticateClient(@Nullable String email,
                                   @Nullable String password);

}
