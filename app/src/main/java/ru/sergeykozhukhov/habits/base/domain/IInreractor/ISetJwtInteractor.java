package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public interface ISetJwtInteractor {
    void setJwt(Jwt jwt);
}
