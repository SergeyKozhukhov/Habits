package ru.sergeykozhukhov.habits.base.domain.IInreractor.builder;

import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;

public interface IGetJwtValue {

    String getValue() throws GetJwtException;
}
