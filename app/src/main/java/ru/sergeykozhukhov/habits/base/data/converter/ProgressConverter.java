package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.data.ProgressData;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public class ProgressConverter implements IConverter<ProgressData, Progress> {
    @NonNull
    @Override
    public Progress convertTo(@NonNull ProgressData progressData) {
        return new Progress(
                progressData.getIdProgress(),
                progressData.getIdProgressServer(),
                progressData.getIdHabit(),
                progressData.getDate());
    }

    @NonNull
    @Override
    public ProgressData convertFrom(@NonNull Progress progress) {
        return new ProgressData(
                progress.getIdProgress(),
                progress.getIdProgressServer(),
                progress.getIdHabit(),
                progress.getDate());
    }
}
