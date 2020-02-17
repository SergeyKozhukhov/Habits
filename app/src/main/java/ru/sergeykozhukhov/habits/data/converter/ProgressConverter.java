package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.domain.Progress;

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
