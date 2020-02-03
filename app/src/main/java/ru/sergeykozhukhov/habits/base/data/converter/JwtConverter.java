package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.data.model.JwtData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Jwt;

public class JwtConverter implements IConverter<JwtData, Jwt> {
    @NonNull
    @Override
    public Jwt convertTo(@NonNull JwtData jwtData) {
        return new Jwt(jwtData.getJwt());
    }

    @NonNull
    @Override
    public JwtData convertFrom(@NonNull Jwt jwt) {
        return new JwtData(jwt.getJwt());
    }
}
