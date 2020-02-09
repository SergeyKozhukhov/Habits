package ru.sergeykozhukhov.habits.notes.backup;

import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface ISaveJwtInteractor {
    void saveJwt(Jwt jwt);
}
