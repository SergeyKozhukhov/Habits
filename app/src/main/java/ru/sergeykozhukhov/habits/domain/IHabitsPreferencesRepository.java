package ru.sergeykozhukhov.habits.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Jwt;

/**
 * Итерфейс репозитория (preferences)
 */
public interface IHabitsPreferencesRepository {

    /**
     * Сохранение token (jwt) в preferences
     *
     * @param jwt token (jwt)
     */
    void saveJwt(@NonNull Jwt jwt);

    /**
     * Получение сохраненного token (jwt)
     *
     * @return сохраненный token (jwt)
     */
    @Nullable
    Jwt loadJwt();

    /**
     * Удаление токена
     */
    void deleteJwt();

}
