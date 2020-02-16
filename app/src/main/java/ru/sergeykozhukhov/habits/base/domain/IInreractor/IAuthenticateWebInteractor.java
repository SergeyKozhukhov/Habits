package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

/**
 * Интерфейс интерктора входа пользователя в свой аккаунт
 */
public interface IAuthenticateWebInteractor {

    @NonNull
    Single<Jwt> authenticateClient(@Nullable String email,
                                   @Nullable String password);

}
