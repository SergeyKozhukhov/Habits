package ru.sergeykozhukhov.habits.notes.backup;

import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface ISetJwtInteractor {
    void setJwt(Jwt jwt);
}
