package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;

/**
 * Конвертер списка HabitWithProgresses моделей между data и domain слоями
 */
public class HabitWithProgressesListConverter implements IConverter<List<HabitWithProgressesData>, List<HabitWithProgresses>> {

    /**
     * Конвертер HabitWithProgresses модели между data и domain слоями
     */
    private final HabitWithProgressesConverter habitWithProgressesConverter;

    public HabitWithProgressesListConverter(@NonNull HabitWithProgressesConverter habitWithProgressesConverter) {
        this.habitWithProgressesConverter = habitWithProgressesConverter;
    }

    /**
     * Конвертирование в список моделей domain слоя
     *
     * @param habitWithProgressesDataList список HabitWithProgresses моделей data слоя
     * @return список HabitWithProgresses моделей domain слоя
     */
    @NonNull
    @Override
    public List<HabitWithProgresses> convertTo(@NonNull List<HabitWithProgressesData> habitWithProgressesDataList) {

        List<HabitWithProgresses> habitWithProgressesList = new ArrayList<>(habitWithProgressesDataList.size());
        for (HabitWithProgressesData habitWithProgressesData : habitWithProgressesDataList) {
            habitWithProgressesList.add(habitWithProgressesConverter.convertTo(habitWithProgressesData));
        }
        return habitWithProgressesList;

    }

    /**
     * Конвертирование в список моделей data слоя
     *
     * @param habitWithProgressesList список HabitWithProgresses моделей domain слоя
     * @return список HabitWithProgresses моделей data слоя
     */
    @NonNull
    @Override
    public List<HabitWithProgressesData> convertFrom(@NonNull List<HabitWithProgresses> habitWithProgressesList) {

        List<HabitWithProgressesData> habitWithProgressesDataList = new ArrayList<>(habitWithProgressesList.size());
        for (HabitWithProgresses habitWithProgresses : habitWithProgressesList) {
            habitWithProgressesDataList.add(habitWithProgressesConverter.convertFrom(habitWithProgresses));
        }
        return habitWithProgressesDataList;
    }
}
