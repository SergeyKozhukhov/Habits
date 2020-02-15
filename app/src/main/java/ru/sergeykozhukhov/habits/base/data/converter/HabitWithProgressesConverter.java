package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;

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
        HabitWithProgressesData habitWithProgressesData = new HabitWithProgressesData();
        habitWithProgressesData.setHabitData(habitConverter.convertFrom(habitWithProgresses.getHabit()));
        habitWithProgressesData.setProgressDataList(progressListConverter.convertFrom(habitWithProgresses.getProgressList()));
        return habitWithProgressesData;
    }
}
