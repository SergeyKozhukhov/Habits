package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.domain.Habit;

/**
 * Конвертер списка Habit моделей между data и domain слоями
 */
public class HabitListConverter implements IConverter<List<HabitData>, List<Habit>> {

    /**
     * Конвертер Habit модели между data и domain слоями
     */
    private final HabitConverter habitConverter;

    public HabitListConverter(@NonNull HabitConverter habitConverter) {
        this.habitConverter = habitConverter;
    }

    /**
     * Конвертирование в список моделей domain слоя
     *
     * @param habitDataList список Habit моделей data слоя
     * @return список Habit моделей domain слоя
     */
    @NonNull
    @Override
    public List<Habit> convertTo(@NonNull List<HabitData> habitDataList) {
        List<Habit> result = new ArrayList<>();
        for (HabitData habitData : habitDataList) {
            result.add(habitConverter.convertTo(habitData));
        }
        return result;
    }

    /**
     * Конвертирование в список моделей data слоя
     *
     * @param habitList список Habit моделей domain слоя
     * @return список Habit моделей data слоя
     */
    @NonNull
    @Override
    public List<HabitData> convertFrom(@NonNull List<Habit> habitList) {
        List<HabitData> result = new ArrayList<>();
        for (Habit habit : habitList) {
            result.add(habitConverter.convertFrom(habit));
        }
        return result;
    }
}
