package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

/**
 * Интерфейс интерактора добавления привычки в базу данных
 */
public interface IInsertHabitDbInteractor {
    @NonNull
    Single<Long> insertHabit(@Nullable String title,
                             @Nullable String description,
                             @Nullable String startDate,
                             @Nullable String duration) throws BuildException;
}
