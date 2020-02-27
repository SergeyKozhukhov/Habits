package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;

/**
 * Интерфейс интераткора получения токена, сохраненного в памяти/preferences
 */
public interface IGetJwtValueInteractor {

    /**
     * Получение строкового представления token (jwt), сохраненного ранее
     *
     * @return строковое представление token (jwt)
     * @throws GetJwtException исключение при ошибке получения токена (jwt) или его отсутсвия
     */
    @NonNull
    String getValue() throws GetJwtException;
}
