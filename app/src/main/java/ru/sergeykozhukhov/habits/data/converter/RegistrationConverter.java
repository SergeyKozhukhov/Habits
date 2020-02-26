package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.RegistrationData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Registration;

/**
 * Конвертер Registration модели между data и domain слоями
 */
public class RegistrationConverter implements IConverter<RegistrationData, Registration> {

    /**
     * Конвертирование в модель domain слоя
     *
     * @param registrationData Registration модель data слоя
     * @return Registration модель domain слоя
     */
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

    /**
     * Конвертирование в модель data слоя
     *
     * @param registration Registration модель domain слоя
     * @return Registration модель data слоя
     */
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
