package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;

public class HabitWithProgressesConverter implements IConverter<HabitWithProgressesData, HabitWithProgresses> {


    private final HabitConverter habitConverter;
    private final ProgressesConverter progressesConverter;

    public HabitWithProgressesConverter(HabitConverter habitConverter, ProgressesConverter progressesConverter) {
        this.habitConverter = habitConverter;
        this.progressesConverter = progressesConverter;
    }

    @NonNull
    @Override
    public HabitWithProgresses convertTo(@NonNull HabitWithProgressesData habitWithProgressesData) {

        return new HabitWithProgresses(
                habitConverter.convertTo(habitWithProgressesData.getHabitData()),
                progressesConverter.convertTo(habitWithProgressesData.getProgressDataList())
        );

    }

    @NonNull
    @Override
    public HabitWithProgressesData convertFrom(@NonNull HabitWithProgresses habitWithProgresses) {
        HabitWithProgressesData habitWithProgressesData = new HabitWithProgressesData();
        habitWithProgressesData.setHabitData(habitConverter.convertFrom(habitWithProgresses.getHabit()));
        habitWithProgressesData.setProgressDataList(progressesConverter.convertFrom(habitWithProgresses.getProgressList()));
        return habitWithProgressesData;
    }
}
