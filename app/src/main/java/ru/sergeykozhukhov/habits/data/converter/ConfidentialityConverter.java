package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;

public class ConfidentialityConverter implements IConverter<ConfidentialityData, Confidentiality> {
    @NonNull
    @Override
    public Confidentiality convertTo(@NonNull ConfidentialityData confidentialityData) {
        return new Confidentiality(
                confidentialityData.getEmail(),
                confidentialityData.getPassword()
        );
    }

    @NonNull
    @Override
    public ConfidentialityData convertFrom(@NonNull Confidentiality confidentiality) {
        return new ConfidentialityData(
                confidentiality.getEmail(),
                confidentiality.getPassword()
        );
    }
}
