package ru.sergeykozhukhov.habits.notes.backup;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.domain.IConverter;

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
