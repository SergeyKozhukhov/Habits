package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;

public interface IRegistrateWebInteractor {

    @NonNull
    Completable registrateClient(@Nullable String firstname,
                                 @Nullable String lastname,
                                 @Nullable String email,
                                 @Nullable String password);

}
