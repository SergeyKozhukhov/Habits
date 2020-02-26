package ru.sergeykozhukhov.habits.domain.iusecase;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

/**
 * Интерфейс получения токена сохраненного в памяти или с preferences
 */
public interface IGetJwtValueInteractor {
    @NonNull
    String getValue() throws GetJwtException;
}
