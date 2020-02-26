package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;

/**
 * Конвертер Confidentiality модели между data и domain слоями
 */
public class ConfidentialityConverter implements IConverter<ConfidentialityData, Confidentiality> {

    /**
     * Конвертирование в модель domain слоя
     *
     * @param confidentialityData Confidentiality модель data слоя
     * @return Confidentiality модель domain слоя
     */
    @NonNull
    @Override
    public Confidentiality convertTo(@NonNull ConfidentialityData confidentialityData) {
        return new Confidentiality(
                confidentialityData.getEmail(),
                confidentialityData.getPassword()
        );
    }

    /**
     * Конвертирование в модель data слоя
     *
     * @param confidentiality Confidentiality модель domain слоя
     * @return Confidentiality модель data слоя
     */
    @NonNull
    @Override
    public ConfidentialityData convertFrom(@NonNull Confidentiality confidentiality) {
        return new ConfidentialityData(
                confidentiality.getEmail(),
                confidentiality.getPassword()
        );
    }
}
