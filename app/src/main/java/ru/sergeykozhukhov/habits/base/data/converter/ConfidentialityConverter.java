package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.data.model.ConfidentialityData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;

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
