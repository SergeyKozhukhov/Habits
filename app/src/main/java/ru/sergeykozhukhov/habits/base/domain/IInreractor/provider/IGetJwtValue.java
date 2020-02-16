package ru.sergeykozhukhov.habits.base.domain.IInreractor.provider;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;

/**
 * Интерфейс получения токена сохраненного в памяти или с preferences
 */
public interface IGetJwtValue {
    @NonNull
    String getValue() throws GetJwtException;
}
