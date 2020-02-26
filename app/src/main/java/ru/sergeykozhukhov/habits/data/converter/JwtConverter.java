package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

/**
 * Конвертер Jwt модели между data и domain слоями
 */
public class JwtConverter implements IConverter<JwtData, Jwt> {

    /**
     * Конвертирование в модель domain слоя
     *
     * @param jwtData Jwt модель data слоя
     * @return Jwt модель domain слоя
     */
    @NonNull
    @Override
    public Jwt convertTo(@NonNull JwtData jwtData) {
        return new Jwt(jwtData.getJwt());
    }

    /**
     * Конвертирование в модель data слоя
     *
     * @param jwt Jwt модель domain слоя
     * @return Jwt модель data слоя
     */
    @NonNull
    @Override
    public JwtData convertFrom(@NonNull Jwt jwt) {
        return new JwtData(jwt.getJwt());
    }
}
