package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Интерфейс интерактора добавления привычки
 */
public interface IInsertHabitDbInteractor {

    /**
     * Добавления привычки в базу данных
     *
     * @param title       название
     * @param description описание
     * @param startDate   дата начала
     * @param duration    продолжительность в кол-ве дней
     * @return single с id добавленной привычки
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    Single<Long> insertHabit(@Nullable String title,
                             @Nullable String description,
                             @Nullable String startDate,
                             @Nullable String duration) throws BuildException;
}
