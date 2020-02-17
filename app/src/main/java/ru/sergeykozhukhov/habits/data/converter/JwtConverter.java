package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

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
