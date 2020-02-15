package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.model.data.RegistrationData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.domain.Registration;

public class  RegistrationConverter implements IConverter<RegistrationData, Registration> {

    @NonNull
    @Override
    public Registration convertTo(@NonNull RegistrationData registrationData) {
        return new Registration(
                registrationData.getFirstName(),
                registrationData.getLastName(),
                registrationData.getEmail(),
                registrationData.getPassword()
        );
    }

    @NonNull
    @Override
    public RegistrationData convertFrom(@NonNull Registration registration) {
        return new RegistrationData(
                registration.getFirstName(),
                registration.getLastName(),
                registration.getEmail(),
                registration.getPassword()
        );
    }
}
