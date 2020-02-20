package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;

public class HabitWithProgressesConverter implements IConverter<HabitWithProgressesData, HabitWithProgresses> {


    private final HabitConverter habitConverter;
    private final ProgressListConverter progressListConverter;

    public HabitWithProgressesConverter(@NonNull HabitConverter habitConverter,
                                        @NonNull ProgressListConverter progressListConverter) {
        this.habitConverter = habitConverter;
        this.progressListConverter = progressListConverter;
    }

    @NonNull
    @Override
    public HabitWithProgresses convertTo(@NonNull HabitWithProgressesData habitWithProgressesData) {

        return new HabitWithProgresses(
                habitConverter.convertTo(habitWithProgressesData.getHabitData()),
                progressListConverter.convertTo(habitWithProgressesData.getProgressDataList())
        );

    }

    @NonNull
    @Override
    public HabitWithProgressesData convertFrom(@NonNull HabitWithProgresses habitWithProgresses) {

        return new HabitWithProgressesData(
                habitConverter.convertFrom(habitWithProgresses.getHabit()),
                progressListConverter.convertFrom(habitWithProgresses.getProgressList())
        );
    }
}
