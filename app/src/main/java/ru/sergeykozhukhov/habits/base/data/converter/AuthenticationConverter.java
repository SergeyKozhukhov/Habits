package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.data.model.AuthenticationData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;

public class AuthenticationConverter implements IConverter<AuthenticationData, Authentication> {
    @NonNull
    @Override
    public Authentication convertTo(@NonNull AuthenticationData authenticationData) {
        return new Authentication(
                authenticationData.getMessage(),
                authenticationData.getJwt()
        );
    }

    @NonNull
    @Override
    public AuthenticationData convertFrom(@NonNull Authentication authentication) {
        return new AuthenticationData(
                authentication.getMessage(),
                authentication.getJwt()
        );
    }
}
