package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface IHabitsPreferencesRepository {

    void saveJwt(@NonNull Jwt jwt);
    Jwt loadJwt();

}
