package ru.sergeykozhukhov.habits.base.domain.IInreractor.provider;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;

public interface IGetJwtValue {
    @NonNull
    String getValue() throws GetJwtException;
}
