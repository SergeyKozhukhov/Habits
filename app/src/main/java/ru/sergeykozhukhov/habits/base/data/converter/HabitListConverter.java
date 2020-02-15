package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class HabitListConverter implements IConverter <List<HabitData>, List<Habit>> {

    private final HabitConverter habitConverter;

    public HabitListConverter(@NonNull HabitConverter habitConverter) {
        this.habitConverter = habitConverter;
    }

    @NonNull
    @Override
    public List<Habit> convertTo(@NonNull List<HabitData> habitDataList) {
        List<Habit> result = new ArrayList<>();
        for (HabitData habitData: habitDataList) {
            result.add(habitConverter.convertTo(habitData));
        }
        return result;
    }

    @NonNull
    @Override
    public List<HabitData> convertFrom(@NonNull List<Habit> habitList) {
        List<HabitData> result = new ArrayList<>();
        for (Habit habit: habitList) {
            result.add(habitConverter.convertFrom(habit));
        }
        return result;
    }
}
