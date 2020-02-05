package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public interface IHabitsPreferencesRepository {

    void saveJwt(@NonNull Jwt jwt);
    void loadJwt();
    void setJwt(@NonNull Jwt jwt);
    Jwt getJwt();
}
