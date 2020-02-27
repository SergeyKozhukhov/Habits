package ru.sergeykozhukhov.habits.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Jwt;

/**
 * Итерфейс репозитория (preferences)
 */
public interface IHabitsPreferencesRepository {

    /**
     * Сохранение токена
     * @param jwt
     */
    void saveJwt(@NonNull Jwt jwt);

    /**
     * Загрузка токека
     * @return
     */
    @Nullable Jwt loadJwt();

    /**
     * Удаление токена
     */
    void deleteJwt();

}
