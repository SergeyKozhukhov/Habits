package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface IHabitsPreferencesRepository extends IRepository {

    void saveJwt(@NonNull Jwt jwt);
    @Nullable Jwt loadJwt();
    void deleteJwt();

}
