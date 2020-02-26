package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Habit;

/**
 * Конвертер Habit модели между data и domain слоями
 */
public class HabitConverter implements IConverter<HabitData, Habit> {

    /**
     * Конвертирование в модель domain слоя
     *
     * @param habitData Habit модель data слоя
     * @return Habit модель domain слоя
     */
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

    /**
     * Конвертирование в модель data слоя
     *
     * @param habit Habit модель domain слоя
     * @return Habit модель data слоя
     */
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
