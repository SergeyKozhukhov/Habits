package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Интерфейс интерактора получения нового экземпляра класса Habit
 */
public interface IBuildHabitInteractor {

    /**
     * Получение нового экземпляра класса Habit
     *
     * @param title       название
     * @param description описание
     * @param startDate   дата начала
     * @param duration    продолжительность кол-ве в дней
     * @return экземляр класса Habit
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    Habit build(@Nullable String title,
                @Nullable String description,
                @Nullable String startDate,
                @Nullable String duration) throws BuildException;
}
