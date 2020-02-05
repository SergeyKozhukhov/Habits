package ru.sergeykozhukhov.habits.base.data.converter;

import androidx.annotation.NonNull;

import java.util.Date;

import ru.sergeykozhukhov.habits.base.data.model.HabitData;
import ru.sergeykozhukhov.habits.base.domain.IConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

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
                habit.getStartDate() == null? new Date() : habit.getStartDate(),
                habit.getDuration()
        );
    }
}
