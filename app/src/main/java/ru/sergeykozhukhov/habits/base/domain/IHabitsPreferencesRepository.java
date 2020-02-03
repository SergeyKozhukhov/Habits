package ru.sergeykozhukhov.habits.base.domain;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public interface IHabitsPreferencesRepository {

    void setJwt(Jwt jwt);
    Jwt getJwt();

}
