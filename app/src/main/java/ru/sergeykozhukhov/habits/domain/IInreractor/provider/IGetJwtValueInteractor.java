package ru.sergeykozhukhov.habits.domain.IInreractor.provider;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;

/**
 * Интерфейс получения токена сохраненного в памяти или с preferences
 */
public interface IGetJwtValueInteractor {
    @NonNull
    String getValue() throws GetJwtException;
    @NonNull
    Single<Jwt> getValueSingle();
}
