package ru.sergeykozhukhov.habits.data.converter;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.domain.IConverter;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;

/**
 * Конвертер HabitWithProgresses модели между data и domain слоями
 */
public class HabitWithProgressesConverter implements IConverter<HabitWithProgressesData, HabitWithProgresses> {


    /**
     * Конвертер Habit модели между data и domain слоями
     */
    private final HabitConverter habitConverter;

    /**
     * Конвертер списка Progresses моделей между data и domain слоями
     */
    private final ProgressListConverter progressListConverter;

    public HabitWithProgressesConverter(@NonNull HabitConverter habitConverter,
                                        @NonNull ProgressListConverter progressListConverter) {
        this.habitConverter = habitConverter;
        this.progressListConverter = progressListConverter;
    }

    /**
     * Конвертирование в модель domain слоя
     *
     * @param habitWithProgressesData HabitWithProgresses модель data слоя
     * @return HabitWithProgresses модель domain слоя
     */
    @NonNull
    @Override
    public HabitWithProgresses convertTo(@NonNull HabitWithProgressesData habitWithProgressesData) {

        return new HabitWithProgresses(
                habitConverter.convertTo(habitWithProgressesData.getHabitData()),
                progressListConverter.convertTo(habitWithProgressesData.getProgressDataList())
        );

    }

    /**
     * Конвертирование в модель data слоя
     *
     * @param habitWithProgresses HabitWithProgresses модель domain слоя
     * @return HabitWithProgresses модель data слоя
     */
    @NonNull
    @Override
    public HabitWithProgressesData convertFrom(@NonNull HabitWithProgresses habitWithProgresses) {

        return new HabitWithProgressesData(
                habitConverter.convertFrom(habitWithProgresses.getHabit()),
                progressListConverter.convertFrom(habitWithProgresses.getProgressList())
        );
    }
}
