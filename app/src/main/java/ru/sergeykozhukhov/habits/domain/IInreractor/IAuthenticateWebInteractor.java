package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

/**
 * Интерфейс интерктора входа пользователя в свой аккаунт
 */
public interface IAuthenticateWebInteractor {

    @NonNull
    Single<Jwt> authenticateClient(@Nullable String email,
                                   @Nullable String password);

}