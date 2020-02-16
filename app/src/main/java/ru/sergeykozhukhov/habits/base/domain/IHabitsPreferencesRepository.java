package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface IHabitsPreferencesRepository extends IRepository {

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
