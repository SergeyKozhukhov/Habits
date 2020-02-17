package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Habit;

public class HabitConverter implements IConverter<HabitData, Habit> {

    @NonNull
    @Override
    public Habit convertTo(@NonNull HabitData habitData) {
        return new Habit(
                habitData.getIdHabit(),
                habitData.getIdHabitServer(),
                habitData.getTitle(),
                habitData.getDescription(),
                habitData.getStartDate(),
                habitData.getDuration()
        );
    }

    @NonNull
    @Override
    public HabitData convertFrom(@NonNull Habit habit) {
        return new HabitData(
                habit.getIdHabit(),
                habit.getIdHabitServer(),
                habit.getTitle(),
                habit.getDescription(),
                habit.getStartDate(),
                habit.getDuration()
        );
    }
}
